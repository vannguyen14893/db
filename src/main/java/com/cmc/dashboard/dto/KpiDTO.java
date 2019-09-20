package com.cmc.dashboard.dto;

public class KpiDTO {
  private Float numerator;
  private Float denominator;
  private Float value;
public Float getNumerator() {
	return numerator;
}
public void setNumerator(Float numerator) {
	this.numerator = numerator;
}
public Float getDenominator() {
	return denominator;
}
public void setDenominator(Float denominator) {
	this.denominator = denominator;
}
public Float getValue() {
	return value;
}
public void setValue(Float value) {
	this.value = value;
}
public KpiDTO(){
	
}
public KpiDTO(Float numerator, Float denominator, Float value) {
	super();
	this.numerator = numerator;
	this.denominator = denominator;
	this.value = value;
}
}
