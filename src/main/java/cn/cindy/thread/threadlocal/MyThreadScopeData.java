package cn.cindy.thread.threadlocal;


/**
 * ThreadLocal使用场合主要解决多线程中数据因并发产生不一致问题。
 * ThreadLocal为每个线程的中并发访问的数据提供一个副本，通过访问副本来运行业务，
 * <b>这样的结果是耗费了内存，但大大减少了线程同步所带来性能消耗，也减少了线程并发控制的复杂度。</b>
 * 
 * 将当前线程局部变量的值删除，目的是为了减少内存的占用，该方法是JDK 1.5 新增的方法。
 * 需要指出的是，当线程结束后，对应该线程的局部变量将自动被垃圾回收，
 * 所以显式调用该方法清除线程的局部变量并不是必须的操作，但它可以加快内存回收的速度。
 * 
 * protected Object initialValue()返回该线程局部变量的初始值，该方法是一个protected的方法，显然是为了让子类覆盖而设计的。
 * 这个方法是一个延迟调用方法，在线程第1次调用get()或set(Object)时才执行，并且仅执行1次。ThreadLocal中的缺省实现直接返回一个null。
 * hibernate的例子：
 *  private static final ThreadLocal threadSession = new ThreadLocal();  
      
    public static Session getSession() throws InfrastructureException {  
        Session s = (Session) threadSession.get();  
        try {  
            if (s == null) {  
                s = getSessionFactory().openSession();  
                threadSession.set(s);  
            }  
        } catch (HibernateException ex) {  
            throw new InfrastructureException(ex);  
        }  
        return s;  
    }  
 */
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
	
	/**
	 * 线程安全的，因为从ThreaadLocal中取出的实例变量
	 */
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
