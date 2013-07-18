package osgitest;

import java.io.File;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

public class Activator implements BundleActivator {

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
		Activator.context = bundleContext;
		//		Bundle b = context.installBundle("initial@reference:file:/home/luonanqin/Desktop/ListenerTest2.jar");
		//		b.start();
		Bundle[] bs = context.getBundles();
		if (bs != null) {
			for (int i = 0; i < bs.length; i++) {
				System.out.println(bs[i].getLocation());
			}
		}
		System.out.println("OSGiTest start!");
		new Thread(new Runnable() {

			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
					String path = "/home/luonanqin/testosgi/bundles";
					File f = new File(path);
					File[] fs = f.listFiles();
					if (fs != null) {
						for (int i = 0; i < fs.length; i++) {
							String filepath = fs[i].getAbsolutePath();
							Bundle b;
							try {
								b = context.installBundle("initial@reference:file:" + filepath);
								b.start();
							} catch (BundleException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}).start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		System.out.println("OSGiTest stop!");
	}

}
