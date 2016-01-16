package cn.cindy.rare;

import java.io.Externalizable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 *对象的序列化可以通过实现两种接口来实现，若实现的是Serializable接口，则所有的序列化将会自动进行，
 *若实现的是Externalizable接口，则没有任何东西可以自动序列化，
 *需要在writeExternal方法中进行手工指定所要序列化的变量，这与是否被transient修饰无关。
 */
public class ExternalizableTest implements Externalizable {

    private transient String content = "是的，我将会被序列化，不管我是否被transient关键字修饰";
    static String path = "src/main/resources/ExternalizableTest";
    
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(content);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        content = (String) in.readObject();
    }

    public static void main(String[] args) throws Exception {
        
        ExternalizableTest et = new ExternalizableTest();
        ObjectOutput out = new ObjectOutputStream(new FileOutputStream(new File(path)));
        out.writeObject(et);

        ObjectInput in = new ObjectInputStream(new FileInputStream(new File(path)));
        et = (ExternalizableTest) in.readObject();
        System.out.println(et.content);
        System.out.println(et.path);

        out.close();
        in.close();
    }
}