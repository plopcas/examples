<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0"
	xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<!-- Page layout information -->

	<xsl:template match="/">

		<xsl:element name="fo:root">

			<xsl:attribute name="font-family"><xsl:value-of select="'any'" /></xsl:attribute>
			<xsl:attribute name="font-size">10pt</xsl:attribute>

			<fo:layout-master-set>

				<fo:simple-page-master master-name="first-page"
					margin-bottom="1cm" margin-left="1cm" margin-right="1cm"
					margin-top="1cm" page-height="29.7cm" page-width="21cm">
					<fo:region-body region-name="body" margin-bottom="15cm"
						margin-top="0cm" background-color="#ff8200" />
					<fo:region-after region-name="footer" extent="15cm"
						background-color="#ff8200" />
				</fo:simple-page-master>
				<fo:simple-page-master master-name="toc"
					margin-bottom="0cm" margin-left="0cm" margin-right="0cm"
					margin-top="0cm" page-height="29.7cm" page-width="21cm">
					<fo:region-body region-name="body" margin-left="1cm"
						margin-right="1cm" margin-bottom="1cm" margin-top="1cm" />
				</fo:simple-page-master>
				<fo:simple-page-master master-name="content"
					margin-bottom="0cm" margin-left="0cm" margin-right="0cm"
					margin-top="0cm" page-height="29.7cm" page-width="21cm">
					<fo:region-body region-name="body" margin-left="3cm"
						margin-right="3cm" margin-bottom="3cm" margin-top="3cm" />
					<fo:region-before region-name="page-header"
						extent="2cm" background-color="#ff8200" />
					<fo:region-after region-name="page-footer" extent="2cm"
						background-color="#ff8200" />
					<fo:region-start region-name="page-start" extent="2cm"
						background-color="#ff8200" />
					<fo:region-end region-name="page-end" extent="2cm"
						background-color="#ff8200" />
				</fo:simple-page-master>

			</fo:layout-master-set>

			<fo:page-sequence master-reference="first-page">

				<fo:static-content flow-name="footer">
					<xsl:apply-templates select="recipe/description" />
				</fo:static-content>

				<fo:flow flow-name="body">
					<xsl:apply-templates select="recipe/title" />
				</fo:flow>

			</fo:page-sequence>

			<fo:page-sequence master-reference="toc">
				<fo:flow flow-name="body">
					<fo:block font-size="40pt" text-align="center" color="#ff8200">Table
						of Contents
					</fo:block>
					<xsl:call-template name="toc" />
				</fo:flow>
			</fo:page-sequence>

			<fo:page-sequence master-reference="content">
				<fo:static-content flow-name="page-start">
					<fo:block-container reference-orientation="90"
						position="absolute" top="2.1cm" left="0.8cm">
						<fo:block font-size="12pt" font-weight="bold" text-align="right"
							color="#ffffff">
							<xsl:value-of select="'Victoria Sponge'" />
						</fo:block>
					</fo:block-container>
				</fo:static-content>

				<fo:static-content flow-name="page-header">
					<fo:block>
						<fo:leader leader-pattern="space" />
					</fo:block>
				</fo:static-content>

				<fo:static-content flow-name="page-footer">
					<fo:block text-align="center" font-size="14pt" color="#ffffff"
						margin-top="0.8cm">
						<fo:page-number />
					</fo:block>
				</fo:static-content>

				<fo:static-content flow-name="page-end">
					<fo:block>
						<fo:leader leader-pattern="space" />
					</fo:block>
				</fo:static-content>

				<fo:flow flow-name="body">
					<fo:block>
						<xsl:apply-templates select="//ingredients" />
						<fo:block page-break-before="always" />
						<xsl:apply-templates select="//preparation" />
					</fo:block>
				</fo:flow>

			</fo:page-sequence>
		</xsl:element>
	</xsl:template>

	<xsl:template match="title">
		<fo:block text-align="center" font-size="50pt" color="#ffffff"
			margin-top="6cm">
			<xsl:value-of select="."></xsl:value-of>
		</fo:block>
	</xsl:template>

	<xsl:template match="recipe/description">
		<fo:block text-align="center" font-size="14pt" color="#000000"
			padding="1cm" font-style="italic">
			<xsl:value-of select="."></xsl:value-of>
		</fo:block>
		<fo:block font-size="14pt" padding="1cm">
			<fo:leader leader-pattern="space" />
		</fo:block>
		<xsl:apply-templates select="child::node()" />
	</xsl:template>

	<xsl:template match="recipe/description/image">
		<fo:block text-align="center">
			<xsl:variable name="image-url" select="./@location" />
			<fo:external-graphic padding="0cm" margin-top="0cm"
				content-width="5cm" height="5cm" content-height="scale-to-fit"
				scaling="non-uniform" background-color="white" src="url({$image-url})" />
		</fo:block>
	</xsl:template>

	<xsl:template name="toc">
		<xsl:apply-templates select="recipe/ingredients"
			mode="toc" />
		<xsl:apply-templates select="recipe/preparation"
			mode="toc" />
	</xsl:template>

	<xsl:template match="recipe/ingredients" mode="toc">
		<fo:block font-size="20" margin="1cm">
			<fo:basic-link internal-destination="{generate-id()}">
				<xsl:value-of select="'-> Ingredients .......... '" />
				<fo:page-number-citation ref-id="{generate-id()}" />
			</fo:basic-link>
		</fo:block>
	</xsl:template>

	<xsl:template match="recipe/ingredients">
		<fo:block id="{generate-id()}" font-size="20" color="#ff8200"
			margin-top="1cm" space-after="1cm">Ingredients</fo:block>
		<fo:list-block>
			<xsl:apply-templates />
		</fo:list-block>
	</xsl:template>

	<xsl:template match="//ingredient">
		<fo:list-item provisional-distance-between-starts="1cm">
			<fo:list-item-label end-indent="label-end()">
				<fo:block text-align="right">&#x02022;</fo:block>
			</fo:list-item-label>
			<fo:list-item-body start-indent="body-start()">
				<fo:block>
					<xsl:value-of select="." />
				</fo:block>
			</fo:list-item-body>
		</fo:list-item>
	</xsl:template>

	<xsl:template match="recipe/preparation" mode="toc">
		<fo:block font-size="20" margin="1cm">
			<fo:basic-link internal-destination="{generate-id()}">
				<xsl:value-of select="'-> Preparation .......... '" />
				<fo:page-number-citation ref-id="{generate-id()}" />
			</fo:basic-link>
		</fo:block>
	</xsl:template>

	<xsl:template match="recipe/preparation">
		<fo:block id="{generate-id()}" font-size="20" color="#ff8200"
			margin-top="1cm" space-after="1cm">Preparation</fo:block>
		<fo:list-block>
			<xsl:apply-templates />
		</fo:list-block>

		<fo:block margin-top="2cm" text-align="center">
			<fo:external-graphic padding="0cm" margin-top="0cm"
				content-width="6cm" height="5cm" content-height="scale-to-fit"
				scaling="non-uniform" background-color="white" border="0.2cm solid #ff8200"
				src="url('template/cook.png')" />
		</fo:block>

	</xsl:template>

	<xsl:template match="//step">
		<fo:list-item provisional-distance-between-starts="1cm">
			<fo:list-item-label end-indent="label-end()">
				<fo:block text-align="right">
					<xsl:value-of select="./@order" />
				</fo:block>
			</fo:list-item-label>
			<fo:list-item-body start-indent="body-start()">
				<fo:block>
					<xsl:value-of select="." />
				</fo:block>
			</fo:list-item-body>
		</fo:list-item>
	</xsl:template>

</xsl:stylesheet>