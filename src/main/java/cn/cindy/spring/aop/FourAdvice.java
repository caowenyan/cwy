package cn.cindy.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

@Aspect
public class FourAdvice {
  
    @Around("execution(* cn.cindy.spring.aop.*.*(..))")
    public Object processTx(ProceedingJoinPoint jp) throws Throwable{
        System.out.println("Around增强:执行目标方法之前，模拟开始事务...");  
        Object[] args=jp.getArgs();  
        if(args!=null && args.length>0 && args[0].getClass()==String.class){  
            args[0]="被改变的参数";  
        }  
        Object rvt=jp.proceed(args);  
        System.out.println("Around增强:执行目标方法之后，模拟结束事务...");  
        return rvt+" 新增的内容";  
    }  
      
    @Before("execution(* cn.cindy.spring.aop.*.*(..))")
    public void authority(JoinPoint jp){
        System.out.println("Before增强:模拟执行权限检查...");  
        System.out.println("Before增强:被织入增强处理的目标方法为："+  
                jp.getSignature().getName());  
        System.out.println("Before增强：目标方法的参数为："+ Arrays.toString(jp.getArgs()));
        System.out.println("Before增强:被织入增强处理的目标对象为："+jp.getTarget());  
    }  
      
    @AfterReturning(returning="rvt",pointcut="execution(* cn.cindy.spring.aop.*.*(..))")
    public void log(JoinPoint jp,Object rvt){  
        System.out.println("AfterReturning增强：获取目标方法返回值："+rvt);  
        System.out.println("AfterReturning增强：模拟记录日志功能...");  
        System.out.println("AfterReturning增强：被织入增强处理的目标方法为:"+  
                jp.getSignature().getName());  
        System.out.println("AfterReturning增强：目标方法的参数为："+  
                Arrays.toString(jp.getArgs()));  
        System.out.println("AfterReturning增强:被织入增强处理的目标对象为："+  
                jp.getTarget());  
    }  
      
    @After("execution(* cn.cindy.spring.aop.*.*(..))")
    public void release(JoinPoint jp){  
        System.out.println("After增强：模拟方法结束后的释放资源...");  
        System.out.println("After增强：被织入增强处理的目标方法为："+  
                jp.getSignature().getName());  
        System.out.println("After增强：目标方法的参数为："+  
                Arrays.toString(jp.getArgs()));  
        System.out.println("After增强: 被织入增强处理的目标对象为："+  
                jp.getTarget());  
    }  
}  