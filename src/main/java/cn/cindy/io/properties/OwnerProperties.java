package cn.cindy.io.properties;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;
import org.aeonbits.owner.Config.Sources;

@Sources({"classpath:app.properties"})
public interface OwnerProperties extends Config{

	@Key("session.front.name")
	@DefaultValue("frontSession")
	String sessionFrontName();
	
	@Key("session.actual.name")
	@DefaultValue("actualSession")
	String sessionActualName();
	
	@Key("session.action.name")
	@DefaultValue("actionSession")
	String sessionActionName();
	
	@Key("session.max.borrow.count")
	@DefaultValue("10000")
	int sessionMaxBorrowCount();

	@Key("session.pool.max.idle")
	@DefaultValue("200")
	int sessionPoolMaxIdle();
	
	@Key("session.pool.max.total")
	@DefaultValue("200")
	int sessionPoolMaxTotal();
	
	@Key("session.pool.min.idle")
	@DefaultValue("20")
	int sessionPoolMinIdle();
	
	@Key("session.pool.kept.count")
	@DefaultValue("60")
	int sessionPoolKeptCount();
	
	@Key("session.borrow.timeout")
	@DefaultValue("1000")
	int sessionBorrowTimeout();
}
