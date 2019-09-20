package com.cmc.dashboard.controller.rest;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.cmc.dashboard.util.Constants;

import com.cmc.dashboard.model.Skill;
import com.cmc.dashboard.service.SkillService;

import scala.Int;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class SkillController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SkillService skillService;
	@Autowired
	private ServletContext servletContext;
	
	@RequestMapping(value = "/skill/list")
	public ResponseEntity<List<Skill>> listSkill() {
		return new ResponseEntity<>(skillService.listSkill(), HttpStatus.OK);
	}

	@PostMapping(value = "/skill/update")
	public ResponseEntity<Object> updateSkill(@RequestParam String name,
			@RequestParam(value = "file", required = false) MultipartFile fileDocument, @RequestParam int id)
			throws IllegalStateException, IOException {
		String pathToSave = servletContext.getRealPath(Constants.URL_IMG);
		String skillName = name;
		// String image = image;
		Skill find = skillService.findById(id);
		find.setName(skillName);
		
		String url = find.getImage();
		if (fileDocument != null) {
			String extension=FilenameUtils.getExtension(fileDocument.getOriginalFilename());
			File file = new File(pathToSave + "/" + id  + "." + extension);
			fileDocument.transferTo(file);
			url = Constants.URL_IMG+"/"+file.getName();
			find.setImage(url);
		}
		skillService.save(find);
		find.setImage(url == "" ? "" : Constants.BASE_URL + url+"?t="+new Date().getTime());
		return new ResponseEntity<>(find, HttpStatus.OK);
	}

	@PostMapping(value = "/skill/create")
	public ResponseEntity<Object> createSkill(@RequestParam String name,
			@RequestParam(value = "file", required = false) MultipartFile fileDocument)
			throws IllegalStateException, IOException {
		Skill s = new Skill();
		String skillName = name;
		String pathToSave = servletContext.getRealPath(Constants.URL_IMG);
		// transfer the received file to the given destination file
		// s.setImage(file.getName());
		s.setName(skillName);
		try {
			skillService.create(s);
			String extension=FilenameUtils.getExtension(fileDocument.getOriginalFilename());
			String url = pathToSave + "/" + s.getSkillId()+"."+extension;
			File file = new File(url);
			fileDocument.transferTo(file);
			s.setImage(Constants.URL_IMG+"/"+file.getName());
			skillService.save(s);
			s.setImage(Constants.BASE_URL + s.getImage());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(s, HttpStatus.OK);
	}

	@DeleteMapping(value = "skill/delete/{id}")
	public ResponseEntity<Boolean> deleteSkill(@PathVariable int id) {
		return new ResponseEntity<>(skillService.delete(id), HttpStatus.OK);
	}
}
