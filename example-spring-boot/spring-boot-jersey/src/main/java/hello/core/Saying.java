package hello.core;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Saying {

	private String content;

	public Saying() {
	}

	public Saying(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}