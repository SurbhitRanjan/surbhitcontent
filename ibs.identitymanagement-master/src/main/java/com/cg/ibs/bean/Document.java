package com.cg.ibs.bean;

public class Document {
	private String pathOfDocument;
	private String nameOfDocument;
	
	public Document() {
		super();
	}

	public Document(String pathOfDocument, String nameOfDocument) {
		super();
		this.pathOfDocument = pathOfDocument;
		this.nameOfDocument = nameOfDocument;
	}
	
	public String getPathOfDocument() {
		return pathOfDocument;
	}
	public void setPathOfDocument(String pathOfDocument) {
		this.pathOfDocument = pathOfDocument;
	}
	public String getNameOfDocument() {
		return nameOfDocument;
	}
	public void setNameOfDocument(String nameOfDocument) {
		this.nameOfDocument = nameOfDocument;
	}
	
	
}
