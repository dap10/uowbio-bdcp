<html>
  <head>
	  <title>JsTree demo</title>
		<g:javascript library="jquery" plugin="jquery"/>
		<jsTree:resources/>
  </head>

  <body>
    
    <h1 id="dhead">jsTree v.1.0</h1>
<h1>DEMO</h1>
<h2>Creating a tree, binding events, using the instance</h2>
<div id="description2">
<p>Here is how you create an instance, bind an event and then get the instance.</p>
<div id="demo1" class="demo" style="height:100px;">
	<ul>
		<li id="phtml_1">
			<a href="#">Root node 1</a>
			<ul>
				<li id="phtml_2">
					<a href="#">Child node 1</a>
				</li>
				<li id="phtml_3">
					<a href="#">Child node 2</a>
				</li>
			</ul>
		</li>
		<li id="phtml_4">
			<a href="#">Root node 2</a>
		</li>
	</ul>
</div>
<script type="text/javascript" class="source below">
$(function () {
	// TO CREATE AN INSTANCE
	// select the tree container using jQuery
	$("#demo1")
		// call `.jstree` with the options object
		.jstree({
			// the `plugins` array allows you to configure the active plugins on this instance
			"plugins" : ["themes","html_data","ui","crrm","hotkeys"],
			// each plugin you have included can have its own config object
			"core" : { "initially_open" : [ "phtml_1" ] }
			// it makes sense to configure a plugin only if overriding the defaults
		})
		// EVENTS
		// each instance triggers its own events - to process those listen on the container
		// all events are in the `.jstree` namespace
		// so listen for `function_name`.`jstree` - you can function names from the docs
		.bind("loaded.jstree", function (event, data) {
			// you get two params - event & data - check the core docs for a detailed description
		});
	// INSTANCES
	// 1) you can call most functions just by selecting the container and calling `.jstree("func",`
	setTimeout(function () { $("#demo1").jstree("set_focus"); }, 500);
	// with the methods below you can call even private functions (prefixed with `_`)
	// 2) you can get the focused instance using `$.jstree._focused()`. 
	setTimeout(function () { $.jstree._focused().select_node("#phtml_1"); }, 1000);
	// 3) you can use $.jstree._reference - just pass the container, a node inside it, or a selector
	setTimeout(function () { $.jstree._reference("#phtml_1").close_node("#phtml_1"); }, 1500);
	// 4) when you are working with an event you can use a shortcut
	$("#demo1").bind("open_node.jstree", function (e, data) {
		// data.inst is the instance which triggered this event
		data.inst.select_node("#phtml_2", true);
	});
	setTimeout(function () { $.jstree._reference("#phtml_1").open_node("#phtml_1"); }, 2500);
});
</script>
</div>

<h2>Doing something when the tree is loaded</h2>
<div id="description">
<p>You can use a few events to do that.</p>
<div id="demo2" class="demo" style="height:100px;">
	<ul>
		<li id="rhtml_1" class="jstree-open">
			<a href="#">Root node 1</a>
			<ul>
				<li id="rhtml_2">
					<a href="#">Child node 1</a>
				</li>
				<li id="rhtml_3">
					<a href="#">Child node 2</a>
				</li>
			</ul>
		</li>
		<li id="rhtml_4">
			<a href="#">Root node 2</a>
		</li>
	</ul>
</div>
<script type="text/javascript" class="source below">
// Note method 2) and 3) use `one`, this is because if `refresh` is called those events are triggered
$(function () {
	$("#demo2")
		.jstree({ "plugins" : ["themes","html_data","ui"] })
		// 1) the loaded event fires as soon as data is parsed and inserted
		.bind("loaded.jstree", function (event, data) { })
		// 2) but if you are using the cookie plugin or the core `initially_open` option:
		.one("reopen.jstree", function (event, data) { })
		// 3) but if you are using the cookie plugin or the UI `initially_select` option:
		.one("reselect.jstree", function (event, data) { });
});
</script>
</div>

<h2>Doing something when a node is clicked</h2>
<div id="description">
<div id="demo3" class="demo" style="height:100px;">
	<ul>
		<li id="shtml_1" class="jstree-open">
			<a href="#">Root node 1</a>
			<ul>
				<li id="shtml_2">
					<a href="#">Child node 1</a>
				</li>
				<li id="shtml_3">
					<a href="#">Child node 2</a>
				</li>
			</ul>
		</li>
		<li id="shtml_4">
			<a href="#">Root node 2</a>
		</li>
	</ul>
</div>
<script type="text/javascript" class="source below">
$(function () {
	$("#demo3")
		.jstree({ "plugins" : ["themes","html_data","ui"] })
		// 1) if using the UI plugin bind to select_node
		.bind("select_node.jstree", function (event, data) { 
			// `data.rslt.obj` is the jquery extended node that was clicked
			alert(data.rslt.obj.attr("id"));
		})
		// 2) if not using the UI plugin - the Anchor tags work as expected
		//    so if the anchor has a HREF attirbute - the page will be changed
		//    you can actually prevent the default, etc (normal jquery usage)
		.delegate("a", "click", function (event, data) { event.preventDefault(); })
});
</script>
</div>

<h2>Using CSS to make nodes wrap</h2>
<div id="description">
<div id="demo4" class="demo" style="height:120px;">
	<ul>
		<li class="jstree-open">
			<a href="#">Root node 1</a>
			<ul>
				<li>
					<a href="#">Child node 1 with a long text which would normally just cause a scrollbar, but with this line of CSS it will actually wrap, this is not really throughly tested but it works</a>
				</li>
				<li>
					<a href="#">Child node 2</a>
				</li>
			</ul>
		</li>
		<li>
			<a href="#">Root node 2</a>
		</li>
	</ul>
</div>
<style type="text/css" class="source below">
#demo4 a { white-space:normal !important; height: auto; padding:1px 2px; } 
#demo4 li > ins { vertical-align:top; }
#demo4 .jstree-hovered, #demo4 .jstree-clicked { border:0; }
</style>
<script type="text/javascript">
$(function () {
	$("#demo4")
		.jstree({ "plugins" : ["themes","html_data","ui"] });
});
</script>
</div>

<h2>Using CSS to make the nodes bigger</h2>
<div id="description">
<div id="demo5" class="demo" style="height:120px;">
	<ul>
		<li class="jstree-open">
			<a href="#">Root node 1</a>
			<ul>
				<li>
					<a href="#">Child node 1 with a long text which would normally just cause a scrollbar, but with this line of CSS it will actually wrap, this is not really throughly tested but it works</a>
				</li>
				<li>
					<a href="#">Child node 2</a>
				</li>
			</ul>
		</li>
		<li>
			<a href="#">Root node 2</a>
		</li>
	</ul>
</div>
<style type="text/css" class="source below">
#demo5 li { min-height:22px; line-height:22px; }
#demo5 a { line-height:20px; height:20px; font-size:10px; }
#demo5 a ins { height:20px; width:20px; background-position:-56px -17px; } 
</style>
<script type="text/javascript">
$(function () {
	$("#demo5")
		.jstree({ "plugins" : ["themes","html_data","ui"] });
});
</script>
</div>


</div>

</div>
    
  </body>
</html>
