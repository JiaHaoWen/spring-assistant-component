package com.github.jiahaowen.spring.assistant.component.cache.serializer.hession;

import com.caucho.hessian.io.AbstractHessianInput;
import com.caucho.hessian.io.AbstractMapDeserializer;
import com.caucho.hessian.io.IOExceptionWrapper;
import java.io.IOException;
import java.lang.ref.SoftReference;

/**
 * @author jiahaowen
 */
public class SoftReferenceDeserializer extends AbstractMapDeserializer {

    @Override
    public Object readObject(AbstractHessianInput in, Object[] fields) throws IOException {
        try {
            SoftReference<Object> obj=instantiate();
            in.addRef(obj);
            Object value=in.readObject();
            obj=null;
            return new SoftReference<Object>(value);
        } catch(IOException e) {
            throw e;
        } catch(Exception e) {
            throw new IOExceptionWrapper(e);
        }

    }

    protected SoftReference<Object> instantiate() throws Exception {
        Object obj=new Object();
        return new SoftReference<Object>(obj);
    }

}
