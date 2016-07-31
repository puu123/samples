package foo;

import javax.inject.Inject;

public abstract class HogeSupport {
	
	@Inject
	Hoge hoge;

	public Hoge getHoge() {
		return hoge;
	}

	public void setHoge(Hoge hoge) {
		this.hoge = hoge;
	}
}
