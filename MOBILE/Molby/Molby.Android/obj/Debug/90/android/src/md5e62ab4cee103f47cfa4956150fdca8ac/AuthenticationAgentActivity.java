package md5e62ab4cee103f47cfa4956150fdca8ac;


public class AuthenticationAgentActivity
	extends android.app.Activity
	implements
		mono.android.IGCUserPeer
{
/** @hide */
	public static final String __md_methods;
	static {
		__md_methods = 
			"n_onCreate:(Landroid/os/Bundle;)V:GetOnCreate_Landroid_os_Bundle_Handler\n" +
			"n_finish:()V:GetFinishHandler\n" +
			"";
		mono.android.Runtime.register ("Microsoft.IdentityModel.Clients.ActiveDirectory.Internal.Platform.AuthenticationAgentActivity, Microsoft.IdentityModel.Clients.ActiveDirectory", AuthenticationAgentActivity.class, __md_methods);
	}


	public AuthenticationAgentActivity ()
	{
		super ();
		if (getClass () == AuthenticationAgentActivity.class)
			mono.android.TypeManager.Activate ("Microsoft.IdentityModel.Clients.ActiveDirectory.Internal.Platform.AuthenticationAgentActivity, Microsoft.IdentityModel.Clients.ActiveDirectory", "", this, new java.lang.Object[] {  });
	}


	public void onCreate (android.os.Bundle p0)
	{
		n_onCreate (p0);
	}

	private native void n_onCreate (android.os.Bundle p0);


	public void finish ()
	{
		n_finish ();
	}

	private native void n_finish ();

	private java.util.ArrayList refList;
	public void monodroidAddReference (java.lang.Object obj)
	{
		if (refList == null)
			refList = new java.util.ArrayList ();
		refList.add (obj);
	}

	public void monodroidClearReferences ()
	{
		if (refList != null)
			refList.clear ();
	}
}
