<script type="text/javascript">
	jq(function() {
		jq(".ui-tabs").tabs();
	});
</script>

<div class="ui-tabs">
	<ul
		class="ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all"
		role="tablist">
		<li class="ui-state-default ui-corner-top ui-state-active"><a
			class="ui-tabs-anchor" href="#import-your-db">Import your
				Database</a></li>
		<li class="ui-state-default ui-corner-top"><a
			class="ui-tabs-anchor" href="#custom-indexing">Custom Indexing</a></li>
		<li class="ui-state-default ui-corner-top"><a
			class="ui-tabs-anchor" href="#db-searches-tab">Database Searches</a></li>
		<li class="ui-state-default ui-corner-top"><a
			class="ui-tabs-anchor" href="#docs-searches-tab">Document
				Searches</a></li>
	</ul>

	<div id="import-your-db"><%@ include file="importDatabase.jsp"%></div>
	<div id="custom-indexing"><%@ include file="customIndexing.jsp"%></div>
	<div id="db-searches-tab"><%@ include file="databaseSearches.jsp"%></div>
	<div id="docs-searches-tab"><%@ include
			file="documentSearches.jsp"%></div>

</div>