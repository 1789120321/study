package activator;

import listenerTest.TestListener;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.espertech.esper.client.UpdateListener;

public class ListenerActivator implements BundleActivator {

	private static BundleContext context;

	public static BundleContext getContext() {
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
		ListenerActivator.context = bundleContext;
		//		Bundle b = context.installBundle("initial@reference:file:workspaces/cep/testosgi/ITestOSGi/target/ITestOSGi-0.0.1-SNAPSHOT.jar");
		//		b.start();
		Bundle[] bs = context.getBundles();
		if (bs != null) {
			for (int i = 0; i < bs.length; i++) {
				System.out.println(bs[i].getLocation());
			}
		}
		//		Activator.getContext().registerService(UpdateListener.class, new TestListener(), null);
		bundleContext.registerService(UpdateListener.class, new TestListener(), null);
		//		AverageBatchTest abt = new AverageBatchTest();
		//		abt.test();
		System.out.println("ListenerTest start!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		ListenerActivator.context = null;
		System.out.println("ListenerTest stop!");
	}

}
