<#assign i = MODIFIED?size>
<#if i == 0>
<h3 class="new"><span style="color:green">✚</span> No new files!</span><br/></h3>
<h3 class="new"><span style="color:green">✚</span> No new files!</span><br/></h3>
<#else>
<#list NEW as newFile>
<h3 class="new"><span style="color:green">✚</span> File <code>${newFile}</code><span style="color:green"> has been added!</span><br/></h3>
</#list>
</#if>