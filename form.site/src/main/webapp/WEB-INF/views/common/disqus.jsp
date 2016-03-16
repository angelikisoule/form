<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div id="disqus_thread"></div>
<script type="text/javascript">
	var disqus_shortname = 'ladylikegr';
	var disqus_url='${article.alternate}';
	(function() {
    	var dsq = document.createElement('script'); dsq.type = 'text/javascript'; dsq.async = true;
		dsq.src = '//' + disqus_shortname + '.disqus.com/embed.js';
		(document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(dsq);
	})();
</script>
<noscript>Please enable JavaScript to view the <a href="https://disqus.com/?ref_noscript" rel="nofollow">comments powered by Disqus.</a></noscript>			
<script type="text/javascript">
    var disqus_shortname = 'ladylikegr';
    (function () {
        var s = document.createElement('script'); s.async = true;
        s.type = 'text/javascript';
        s.src = '//' + disqus_shortname + '.disqus.com/count.js';
        (document.getElementsByTagName('HEAD')[0] || document.getElementsByTagName('BODY')[0]).appendChild(s);
    }());
</script>
<script type="text/javascript">
  var reset_disqus = function(){
    DISQUS.reset({
      reload: true,
      config: function () {          
        
        
      }
    });
  };  
  window.addEventListener("orientationchange", function() {
	// Announce the new orientation number	
    reset_disqus(); 
}, false);
  
</script>