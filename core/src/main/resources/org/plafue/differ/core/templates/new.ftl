<div class="new changes-div">
<#assign i = NEW?size>
<#if i == 0>
<h3><span style="color:green">✚</span>&nbsp;No new files!</span><br/></h3>
<#else>
<#list NEW as newFile>
<h3><span style="color:green">✚</span>&nbsp;<code>${newFile}</code><span style="color:green"> has been added!</span><br/></h3>
</#list>
</#if>
</div>