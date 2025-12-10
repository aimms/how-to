VPN Troubleshooting FAQ (Customer Side)
============================================

.. meta::
    :keywords: AIMMS, headless execution, AimmsCmd, Docker, REST service, automation, batch processing, SessionArgument, AIMMS Cloud
    :description: Comprehensive guide to setting up AIMMS headless execution using AimmsCmd on a local machine, within a Docker container, and via the automated AIMMS Cloud Tasks environment.


.. spelling:word-list::
    
    initTask
    frontends
    scalable



This FAQ is designed to help customers diagnose and resolve common issues when connecting to our Azure Virtual Network Gateway via Site-to-Site VPN.

1. The VPN tunnel does not establish
------------------------------------

**Possible causes:**

- Pre-shared key mismatch – Verify that the shared key is the same on both sides.
- Incorrect VPN type/protocol – Ensure your device is configured for IKEv2/IPsec (unless explicitly agreed otherwise).
- Firewall blocking UDP ports 500/4500 – Confirm your firewall allows IPsec/IKE traffic.

2. IKE Phase 1 succeeds but Phase 2 fails
-----------------------------------------

**Possible causes:**

- Routing or NAT issues – Ensure correct local and remote address spaces are defined, and NAT rules (if used) are applied properly.
- Default route (0.0.0.0/0) is included – Avoid advertising the default route unless explicitly supported.

3. IP ranges overlap with Azure or other customers
--------------------------------------------------

**Possible causes:**

- Overlapping subnets cause routing conflicts.

**Resolution options:**

- Change/reduce the size of the IP ranges.
- Implement NAT rules on the customer side.

4. Intermittent VPN connection drops
------------------------------------

**Possible causes:**

- Lifetime settings mismatch – Ensure that IKE/IPsec lifetimes (seconds/KBs) match on both ends.
- Unstable internet connectivity – Check for ISP packet loss or latency spikes.
- Device resource limits – Some on-prem devices struggle under high load. If multiple VPNs are present on the device, this may cause performance issues.

5. Traffic not flowing even though tunnel is up
-----------------------------------------------

**Possible causes:**

- Incorrect IP ranges configured – Verify that the exact subnets provided are entered into your configuration.
- Asymmetric routing – Ensure that return routes are present.
- Firewall rules – Your local firewall may be blocking the Azure subnets.
- NAT not applied consistently – If NAT is used, make sure both inbound and outbound rules are configured.

6. Only some subnets are reachable
----------------------------------

**Possible causes:**

- Missing routes in your VPN device configuration.
- Only a subset of the agree
