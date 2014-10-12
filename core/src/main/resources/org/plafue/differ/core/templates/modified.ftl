<div class="mod changes-div">
<#assign i = MODIFIED?size>
<#if i == 0>
<h3><span style="color:#494F8C">✎</span>&nbsp;No modified files!</span><br/></h3>
<#else>
  <#list MODIFIED as modifiedFile>
  <h3><span style="color:#494F8C">✎</span>&nbsp;<code>${modifiedFile.NAME}</code><span style="color:#494F8C"> has been modified:</span></h3>
  <div id="${modifiedFile.NAME}"></div>
  <script>
  var orig = ${modifiedFile.ORIGINAL};
  var rev = ${modifiedFile.REVISION};
  initUI("${modifiedFile.NAME}",orig,rev)</script>
  </#list>
</#if>
</div>