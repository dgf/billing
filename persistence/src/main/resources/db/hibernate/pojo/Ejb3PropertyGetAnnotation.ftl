<#if ejb3>
<#if pojo.hasIdentifierProperty()>
<#if property.equals(clazz.identifierProperty)>
 ${pojo.generateAnnIdGenerator()}
<#-- configure strategy -->
<#if !pojo.isComponent(clazz.identifierProperty) >
@${pojo.importType("javax.persistence.GeneratedValue")}
</#if>
<#-- /configure strategy -->
<#-- if this is the id property (getter)-->
<#-- explicitly set the column name for this property-->
</#if>
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
${pojo.generateBasicAnnotation(property)}
<#-- map joda times -->
<#if pojo.getJavaTypeName(property, jdk5) == "LocalDate">
@${pojo.importType("org.hibernate.annotations.Type")}(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
</#if>
<#-- /map joda times -->
${pojo.generateAnnColumnAnnotation(property)}
</#if>
</#if>