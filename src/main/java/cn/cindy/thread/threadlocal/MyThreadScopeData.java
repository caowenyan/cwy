package cn.cindy.thread.threadlocal;

class MyThreadScopeData{
	private String name;
	private int age;
	
	private static MyThreadScopeData instance = null;//new MyThreadScopeDate();
	private static ThreadLocal<MyThreadScopeData> local = new ThreadLocal<MyThreadScopeData>();
	
	public static synchronized MyThreadScopeData getInstance(){
		if(instance==null){
			instance = new MyThreadScopeData();
		}
		return instance;
	}
	
	public static MyThreadScopeData getThreadInstance(){
		MyThreadScopeData instance = local.get();
		if(instance==null){
			instance = new MyThreadScopeData();
			local.set(instance);
		}
		return instance;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
}
