package listenertest2;

import listenerTest2.Test2Listener;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import test.AverageBatchTest;

import com.espertech.esper.client.UpdateListener;

public class Listener2Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Listener2Activator.context = bundleContext;
		//		Activator.getContext().registerService(UpdateListener.class, new Test2Listener(), null);
		bundleContext.registerService(UpdateListener.class, new Test2Listener(), null);
		AverageBatchTest abt = new AverageBatchTest();
		abt.test();
		System.out.println("ListenerTest2 start!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Listener2Activator.context = null;
		System.out.println("ListenerTest2 stop!");
	}

}
