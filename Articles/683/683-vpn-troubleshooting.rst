VPN Troubleshooting Guide (Customer Side)
============================================

.. meta::
    :keywords: AIMMS, headless execution, AimmsCmd, Docker, REST service, automation, batch processing, SessionArgument, AIMMS Cloud
    :description: Comprehensive guide to setting up AIMMS headless execution using AimmsCmd on a local machine, within a Docker container, and via the automated AIMMS Cloud Tasks environment.


This troubleshooting guide is designed to help customers diagnose and resolve common issues when connecting to our 
Azure Virtual Network Gateway via Site-to-Site VPN. 

Site-to-Site VPN issues generally fall into one of three categories, which align with the tunnel establishment process:

* Phase 1 Failure (IKE): The two endpoints cannot agree on security parameters or authenticate, preventing the tunnel from being built. *Check authentication, protocols, and firewall ports (UDP 500/4500).*
* Phase 2 Failure (IPsec): Phase 1 succeeds, but the data security association cannot be created, often due to routing or policy definition errors. *Check local/remote address spaces and NAT configurations.*
* Tunnel Up, No Traffic: The tunnel is established, but packets are blocked by internal firewall rules, asymmetric routing, or subnet overlap. *Check routing tables, internal firewall policies, and subnet definitions.*

Problem 1: The VPN Tunnel Fails to Establish
-----------------------------------------------

The connection cannot be authenticated or initialized (IKE Phase 1).

Possible Causes
^^^^^^^^^^^^^^^

* Authentication Mismatch: The Pre-shared Key (PSK) is not identical on both endpoints.
* Incorrect Protocol: The VPN device is not configured for the required protocol (IKEv2/IPsec).
* Firewall Blockade: The perimeter firewall is blocking IPsec/IKE traffic.

Action/Resolution
^^^^^^^^^^^^^^^^^

* Verify the Pre-shared Key is identical on both your local VPN device and the Azure Gateway configuration.
* Ensure your device is configured for IKEv2/IPsec (This is the required standard unless otherwise specified.)
* Confirm that your firewall permits traffic on UDP ports 500 (IKE) and 4500 (NAT-T) to and from the Azure Gateway's Public IP. 


Problem 2: IKE Phase 1 Succeeds, but Phase 2 Fails
-----------------------------------------------------

The initial authentication is successful, but the data security association (IPsec) fails due to routing or policy errors.

Possible Causes
^^^^^^^^^^^^^^^

* Routing or NAT Issues: Address spaces are incorrectly defined or NAT rules are missing.
* Default Route Inclusion: Advertising the default route (``0.0.0.0/0``) when it is not supported.

Action/Resolution
^^^^^^^^^^^^^^^^^

* Ensure the Local and Remote Address Spaces defined on both sides are configured exactly as required.
* If NAT is used, verify that the NAT rules are applied properly to the VPN tunnel interface.
* Avoid advertising the default route (``0.0.0.0/0``) over the VPN unless explicitly mandated for a full tunnel configuration.

Problem 3: IP Ranges Overlap with Azure or Other Networks
-----------------------------------------------------------

Overlapping subnets create ambiguous routing, leading to connection failures or instability.

Possible Cause
^^^^^^^^^^^^^^

* Local and remote subnets conflict, creating routing ambiguity.

Action/Resolution
^^^^^^^^^^^^^^^^^

* Change: Modify or reduce the size of the conflicting local IP ranges/subnets.
* Translate: Implement a static NAT rule on the customer side to translate the local IP range into a non-overlapping range for traffic traversing the VPN.

Problem 4: Intermittent VPN Connection Drops
----------------------------------------------

Drops indicate configuration mismatches or underlying network instability.

Possible Causes
^^^^^^^^^^^^^^^

* Lifetime Mismatch: IKE and IPsec Security Association (SA) lifetimes are not aligned.
* Network Stability: High packet loss or latency spikes toward the Azure VPN Gateway IP.
* Device Limits: On-premise VPN device is struggling with high load or multiple tunnels.

Action/Resolution
^^^^^^^^^^^^^^^^^

* Check Configuration: Verify that the IKE and IPsec Security Association (SA) lifetimes (in seconds or KBs) are perfectly aligned on both ends of the tunnel.
* Check Stability: Investigate high packet loss or latency spikes toward the Azure VPN Gateway IP.
* Check Device: Review CPU/memory utilization and performance logs on your on-premise VPN device.

Problem: Tunnel is Up, but Traffic is Not Flowing
------------------------------------------------------

The security association is established, but data packets are blocked by routing or firewall rules.

Possible Causes
^^^^^^^^^^^^^^^

* Incorrect Routing: Missing or incorrect routes for the remote subnets.
* Asymmetric Routing: Return routes are absent or misconfigured.
* Local Firewall Block: Firewall rules block traffic to/from the Azure subnets.
* Inconsistent NAT: NAT rules are missing or only applied in one direction.

Action/Resolution
^^^^^^^^^^^^^^^^^

* Verify Subnets: Verify the exact subnets of the Azure Virtual Network are correctly entered into your local VPN device's routing table.
* Check Asymmetry: Ensure that return routes (Asymmetric Routing) are present and configured correctly on both sides. 
* Check Firewall: Confirm your local firewall rules allow traffic to and from the Azure subnets over the VPN interface.
* Check NAT: If NAT is used, ensure that both inbound and outbound NAT rules are consistently applied to the VPN traffic.


Problem 6: Only a Subset of Subnets are Reachable
--------------------------------------------------------------------

Communication is restricted to only certain parts of the Azure network.

Possible Cause
^^^^^^^^^^^^^^

* Incomplete Configuration: Missing routes for some intended subnets on the local device.
* Agreement Scope: Only a subset of subnets was initially agreed upon and configured on the remote side.

Action/Resolution
^^^^^^^^^^^^^^^^^

* Check your VPN device configuration for missing routes. The routing table must include all intended subnets on the remote network.
* Review the scope of the VPN connection agreement to confirm all subnets were agreed upon and configured on the remote side.