<!doctype html>
<#include "head.ftl">
<body>
  <h1>
    Release diff
  </h1>
  <#include "deleted.ftl">
  <#include "new.ftl">
  <#include "modified.ftl">
  <div class="timestamp">Generated on: ${TIMESTAMP}</div>
</body>
</html>