
# Table of Contents



<table border="2" cellspacing="0" cellpadding="6" rules="groups" frame="hsides">


<colgroup>
<col  class="org-left" />

<col  class="org-left" />

<col  class="org-left" />

<col  class="org-left" />

<col  class="org-left" />

<col  class="org-left" />

<col  class="org-right" />
</colgroup>
<thead>
<tr>
<th scope="col" class="org-left"><b>Risk</b></th>
<th scope="col" class="org-left"><b>Statement</b></th>
<th scope="col" class="org-left"><b>Response</b></th>
<th scope="col" class="org-left"><b>Objective</b></th>
<th scope="col" class="org-left"><b>Likelihood</b></th>
<th scope="col" class="org-left"><b>Impact</b></th>
<th scope="col" class="org-right"><b>Risk Level</b></th>
</tr>
</thead>

<tbody>
<tr>
<td class="org-left">Deleted code</td>
<td class="org-left">Code gets deleted during the project</td>
<td class="org-left">Use git for version control</td>
<td class="org-left">Keep a record of code that can be copied</td>
<td class="org-left">Unlikely</td>
<td class="org-left">Severe</td>
<td class="org-right">5</td>
</tr>
</tbody>

<tbody>
<tr>
<td class="org-left">Illness during project</td>
<td class="org-left">I fall ill and am unable to work on the project</td>
<td class="org-left">Create a plan following MoSCoW, report illness to supervisor</td>
<td class="org-left">Complete the most important tasks first, leaving contingency time, inform supervisor so that they can make accomodations</td>
<td class="org-left">Unlikely</td>
<td class="org-left">Major</td>
<td class="org-right">4</td>
</tr>
</tbody>

<tbody>
<tr>
<td class="org-left">Computer fails</td>
<td class="org-left">The computer that I work on is no longer able to run</td>
<td class="org-left">Keep a backup of important files elsewhere, continue work on another computer</td>
<td class="org-left">Make sure that little data is lost and that I can continue work ASAP</td>
<td class="org-left">Possible</td>
<td class="org-left">Severe</td>
<td class="org-right">10</td>
</tr>
</tbody>

<tbody>
<tr>
<td class="org-left">Bugs in code</td>
<td class="org-left">The code I write has bugs that affect the running of my project</td>
<td class="org-left">Create a test suite with high code coverage, use SonarQube</td>
<td class="org-left">Ensure that code is well tested and checked for bugs, minimising the chance of major bugs affecting project</td>
<td class="org-left">Likely</td>
<td class="org-left">Moderate</td>
<td class="org-right">9</td>
</tr>
</tbody>

<tbody>
<tr>
<td class="org-left">SQL Injection</td>
<td class="org-left">User attempts to run SQL that modifies/alters the database</td>
<td class="org-left">Using Spring's repository beans, adding input validation</td>
<td class="org-left">Prevent SQL injection attacks by escaping user input</td>
<td class="org-left">Expected</td>
<td class="org-left">Major</td>
<td class="org-right">16</td>
</tr>
</tbody>
</table>

