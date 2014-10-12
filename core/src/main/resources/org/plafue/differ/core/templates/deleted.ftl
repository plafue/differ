<div class="del changes-div">
<#assign i = DELETED?size>
<#if i == 0>
<h3><span style="color:#A3073D">✘</span>&nbsp;No deleted files!</span><br/></h3>
<#else>
<#list DELETED as deletedFile>
<h3><span style="color:#A3073D">✘</span>&nbsp;<code>${deletedFile}</code><span style="color:#A3073D"> has been removed!</span><br/></h3>
</#list>
</#if>
</div>