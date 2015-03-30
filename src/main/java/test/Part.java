package test;

import java.io.InputStream;

public class Part {
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getModelNum() {
		return modelNum;
	}
	public void setModelNum(String modelNum) {
		this.modelNum = modelNum;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public InputStream getFile() {
		return file;
	}
	public void setFile(InputStream file) {
		this.file = file;
	}
	String name;
	String type;
	String modelNum;
	InputStream file;
	String fileName;
}
