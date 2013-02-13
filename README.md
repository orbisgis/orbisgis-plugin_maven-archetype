Maven archetypes for an OrbisGIS 4.0 OSGI plugin bundle.
=====

A set of maven archetypes to help the creation of plugins for OrbisGIS 4.0

## OrbisGIS OSGi Minimal Bundle Archetype

osgi-minimal contain the archetype to write a osgi plugin without any
 dependencies with OrbisGIS.
This is a good starting point if you plan to split your plugin into two pieces :

- The computation core, without dependency with OrbisGIS;
- The bridge between your computation core and OrbisGIS features;

The first part can be created from this maven archetype. The second part would
 be created from GDMS archetype for exemple to write one or more custom SQL function(s).
 
 ## OrbisGIS OSGi SQL Bundle Artchetype
 
 osgi-sql contain the archetype to write your own SQL function and to expose it as a new service.
 OrbisGIS will be noticed of this user-defined SQL function and add it in the current GDMS SQL Engine.
