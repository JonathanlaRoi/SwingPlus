package org.bigfoot.swingplus.event;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaConversionException;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.util.function.BiConsumer;

class JPRespondMethod {

    private static final String methodName = "respond";

    private final String accept = "accept";

    private JPListener listener;

    private JPEvent event;

    private Class<? extends JPEvent> eventType;

    protected JPRespondMethod(JPListener listener, JPEvent event, Class<? extends JPEvent> eventType) {
        this.listener = listener;
        this.event = event;
        this.eventType = eventType;
    }

    protected void execute() throws RuntimeException {
//		CallSite site;
        try {
//			MethodType consumer = MethodType.methodType(BiConsumer.class);
//			MethodType consumerType = MethodType.methodType(void.class, Object.class, Object.class);
//
//			MethodType signature = MethodType.methodType(void.class, eventType);
//			MethodHandle target = JPEventManager.caller.findVirtual(listener.getClass(), methodName, signature);

            listener.getClass().getMethod(methodName, eventType).invoke(listener, event);

//			site = LambdaMetafactory.metafactory(JPEventManager.caller,
//					accept,
//					consumer,
//					consumerType,
//					target,
//					target.type());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
//		try {
//			((BiConsumer<JPListener, JPEvent>)site.getTarget().invokeExact()).accept(listener, event);
//		} catch(Throwable t){
//			throw new RuntimeException(t);
//		}
    }
}
