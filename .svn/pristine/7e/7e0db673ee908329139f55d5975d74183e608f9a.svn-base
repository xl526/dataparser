package com.ts.zx.ig.kepserver;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Locale;

import javax.jms.JMSException;

import org.opcfoundation.ua.builtintypes.DataValue;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.builtintypes.LocalizedText;
import org.opcfoundation.ua.core.ApplicationDescription;
import org.opcfoundation.ua.core.ApplicationType;
import org.opcfoundation.ua.core.Attributes;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.core.ReferenceDescription;
import org.opcfoundation.ua.transport.security.SecurityMode;

import com.prosysopc.ua.ApplicationIdentity;
import com.prosysopc.ua.SecureIdentityException;
import com.prosysopc.ua.ServiceException;
import com.prosysopc.ua.SessionActivationException;
import com.prosysopc.ua.StatusException;
import com.prosysopc.ua.client.ConnectException;
import com.prosysopc.ua.client.InvalidServerEndpointException;
import com.prosysopc.ua.client.MonitoredDataItem;
import com.prosysopc.ua.client.MonitoredDataItemListener;
import com.prosysopc.ua.client.Subscription;
import com.prosysopc.ua.client.UaClient;
import com.ts.zx.ig.AQHandler;
import com.ts.zx.ig.XMLUtils;

public class OPCClient {
	
	private static UaClient client;
	private static Subscription sub;

	public static void main(String[] args) throws URISyntaxException, InvalidServerEndpointException, ConnectException,
			SessionActivationException, ServiceException, StatusException, SecureIdentityException, IOException {
		// *** Application Description is sent to the server

		client = new UaClient("opc.tcp://101.37.78.172:49320");
		client.setSecurityMode(SecurityMode.NONE);
		sub = new Subscription();
		ApplicationDescription appDescription = new ApplicationDescription();
		appDescription.setApplicationName(new LocalizedText("SimpleClient", Locale.ENGLISH));
		// 'localhost' (all lower case) in the URI is converted to the actual
		// host name of the computer in which the application is run
		appDescription.setApplicationUri("urn:localhost:UA:SimpleClient");
		appDescription.setProductUri("urn:prosysopc.com:UA:SimpleClient");
		appDescription.setApplicationType(ApplicationType.Client);

//		final ApplicationIdentity identity = ApplicationIdentity.loadOrCreateCertificate(appDescription, "ts.zx",
//				"opcclient", new File("E://Development"), true);
//		client.setApplicationIdentity(identity);
		
		final ApplicationIdentity identity = new ApplicationIdentity();
		identity.setApplicationDescription(appDescription);
		client.setApplicationIdentity(identity);
		client.connect();

		client.getAddressSpace().setMaxReferencesPerNode(1000);
		client.getAddressSpace().setReferenceTypeId(Identifiers.HierarchicalReferences);

		for (ReferenceDescription rd : client.getAddressSpace().browse(Identifiers.RootFolder)) {
			if (rd.getBrowseName().getName().contains("Object")) {
				for (ReferenceDescription rd1 : client.getAddressSpace().browse(rd.getNodeId())) {
					if (rd1.getBrowseName().getName().contains("factory49")) {

						for (ReferenceDescription rd2 : client.getAddressSpace().browse(rd1.getNodeId())) {
							if (!(rd2.getBrowseName().getName().contains("System")
									|| rd2.getBrowseName().getName().contains("Statistics"))) {
								literator(rd2.getNodeId());
							}
						}
					}
				}
			}
		}
        
		client.addSubscription(sub);
		DataValue value = client.readValue(Identifiers.Server_ServerStatus_State);
		System.out.println(value);
		//client.disconnect();

	}

	public static void literator(ExpandedNodeId expandedNodeId) throws ServiceException, StatusException {
		for (ReferenceDescription rd : client.getAddressSpace().browse(expandedNodeId)) {
			
				MonitoredDataItem item = new MonitoredDataItem(rd.getNodeId(), Attributes.Value);
				
				item.setDataChangeListener(new MonitoredDataItemListener() {	
					@Override
					public void onDataChange(MonitoredDataItem item, DataValue prevalue, DataValue value) {
						System.out.println(item.getNodeId()+":"+value.getValue());
						String equipmentName = KepserverDataHelper.parseEquipmentName(item.getNodeId().toString());
						String paramName = KepserverDataHelper.parseParamName(item.getNodeId().toString());
						String paramValue = value.getValue().toString();
						
						String equipmentsXML = XMLUtils.kepserverData2XML(equipmentName, paramName, paramValue);
						System.out.println(equipmentsXML);
						
						try {
							AQHandler.send(equipmentsXML);
						} catch (JMSException e) {
							e.printStackTrace();
						}
						
					}
				});
				sub.addItem(item);
		}
	}

}

