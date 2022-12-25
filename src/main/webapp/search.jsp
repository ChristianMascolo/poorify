<%@ page import="navigation.ResultsContainer" %>
<%@ page import="profile.ArtistBean" %>
<%@ page import="profile.UserBean" %>
<section id="searchpage">

  <% ResultsContainer results = (ResultsContainer) session.getAttribute("Results"); %>

  <h1>USERS</h1>
  <% for(UserBean u: results.getUsers()) { %>
    <h2><%= u.getAlias() %></h2>
  <% } %>

  <h1>ARTISTS</h1>
  <% for(ArtistBean a: results.getArtists()) { %>
    <h2><%= a.getAlias() %></h2>
  <% } %>

</section>