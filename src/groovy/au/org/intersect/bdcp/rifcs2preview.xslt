<?xml version="1.0"?>
<xsl:stylesheet version="1.0" 
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:ro="http://ands.org.au/standards/rif-cs/registryObjects"
        xmlns:xs="http://www.w3.org/2001/XMLSchema"
        xmlns="http://www.w3.org/1999/xhtml"
        >

	<xsl:output method="xml" encoding="UTF-8" indent="yes" omit-xml-declaration="yes"/>

	<xsl:template match="ro:registryObjects">
			<xsl:apply-templates select="ro:registryObject"/>
	</xsl:template>

	<xsl:template match="ro:registryObject">
        <table summary="Registry Object" class="recordTable">
	<tbody class="recordFields">
		<tr>
			<td>Key: </td>
			<td>
				<xsl:value-of select="ro:key/text()"/>			
			</td>
		</tr>
		<xsl:apply-templates select="ro:collection"/>
	</tbody>
        </table>
	</xsl:template>

	<xsl:template match="ro:collection">

		<tr>
			<td>Collection:</td>
			<td colspan="1" style="width:100%;"><h1><xsl:value-of select="ro:name[@type='primary']/ro:namePart/text()"/></h1></td>
		</tr>
		<tr>
			<td>Type:</td>
			<td colspan="1" style="width:100%;"><xsl:value-of select="@type"/></td>
		</tr>

<!-- DESCRIPTIONS -->
		<tr>
			<td>Description:</td>
			<td colspan="1">
			<ul><xsl:value-of select="ro:description[@type='full']/text()"/></ul>
			</td>
		</tr>
		<tr>
			<td>Subjects:</td>
			<td colspan="1">
			<ul><xsl:apply-templates select="ro:subject"/></ul>
			</td>
		</tr>
		<tr>
			<td>Collection rights:</td>
			<td colspan="1">
			<span><xsl:value-of select="ro:description[@type='rights']/text()"/></span>
			</td>
		</tr>
		<xsl:if test="ro:location/ro:address/ro:electronic[@type='email']">
		<tr>
			<td>Email:</td>
			<td colspan="2">
				<u><xsl:value-of select="ro:location/ro:address/ro:electronic[@type='email']/ro:value/text()"/></u>
			</td>
		</tr>
		</xsl:if>
		<xsl:if test="ro:location/ro:address/ro:physical">
		<tr>
			<td>Contact Details:</td>
			<td colspan="2">
				<pre><xsl:value-of select="ro:location/ro:address/ro:physical/ro:addressPart/text()"/></pre>
			</td>
		</tr>
		</xsl:if>

	</xsl:template>

	<xsl:template match="ro:subject">
	<li><xsl:value-of select="text()" /></li>
	</xsl:template>

</xsl:stylesheet>
