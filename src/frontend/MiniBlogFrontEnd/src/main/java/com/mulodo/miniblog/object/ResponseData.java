package com.mulodo.miniblog.object;

public class ResponseData {
	
	private Meta meta;
	private Data data;
	private String header;
	
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public Meta getMeta() {
		if(meta !=  null){
			return meta;
		}else{
			return null;
		}
		
	}
	public void setMeta(Meta meta) {
		this.meta = meta;
	}
	public Data getData() {
		if(data !=  null){
			return data;
		}else{
			return null;
		}
	}
	public void setData(Data data) {
		this.data = data;
	}
	
	
}
