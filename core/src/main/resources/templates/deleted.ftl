<#assign i = DELETED?size>
<#if i == 0>
<h3 class="del"><span style="color:#A3073D">✘</span> No deleted files!</span><br/></h3>
<#else>
<#list DELETED as deletedFile>
<h3 class="del"><span style="color:#A3073D">✘</span> File <code>${deletedFile}</code><span style="color:#A3073D"> has been removed!</span><br/></h3>
</#list>
</#if>