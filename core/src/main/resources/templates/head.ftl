<head>
  <meta charset="utf-8"/>
  <title>Release diff</title>
  <#include "/scripts.ftl">
  <#include "/styles.ftl">
  <script>
      function initUI(div, original, revision) {

        var target = document.getElementById(div);
        var extension = div.substr(div.lastIndexOf('.')+1,div.length);

        var mode = "text/x-asciidoc";

        if(extension == "feature"){
            mode = "text/x-feature";
        }

        dv = CodeMirror.MergeView(target, {
          value: original,
          origLeft: null,
          orig: revision,
          lineNumbers: true,
          mode: mode,
          highlightDifferences: true,
          showMergeArrows: false
        });
      }
  </script>
</head>