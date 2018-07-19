<#if ejb3>
<#if pojo.hasIdentifierProperty()>
<#compress>
<#if property.equals(clazz.identifierProperty)>
${pojo.generateAnnIdGenerator()}
<#-- configure strategy -->
<#if !pojo.isComponent(clazz.identifierProperty)>
@${pojo.importType("javax.persistence.GeneratedValue")}
</#if>
<#-- /configure strategy -->
<#-- if this is the id property (getter)-->
<#-- explicitly set the column name for this property-->
</#if>
</#compress>
</#if>
<#if c2h.isOneToOne(property)>
${pojo.generateOneToOneAnnotation(property, md)}
<#elseif c2h.isManyToOne(property)>
${pojo.generateManyToOneAnnotation(property)}
<#--TODO support optional and targetEntity-->
${pojo.generateJoinColumnsAnnotation(property, md)}
<#elseif c2h.isCollection(property)>
${pojo.generateCollectionAnnotation(property, md)}
<#else>
<#compress>
<#-- timestamps -->
<#if property.name == "createdAt">
  ${property.setInsertable(false)}
  ${property.setUpdateable(false)}
</#if>
</#compress>
${pojo.generateAnnColumnAnnotation(property)}
${pojo.generateBasicAnnotation(property)}
<#-- /timestamps -->
</#if>
</#if>