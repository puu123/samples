package example;

public class Foo {

	private String bar;

	public String getBar() {
		return bar;
	}

	public void setBar(String bar) {
		this.bar = bar;
	}

	@Override
	public String toString() {
		return "Foo [bar=" + bar + "]";
	}
}
