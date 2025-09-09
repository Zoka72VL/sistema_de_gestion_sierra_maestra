// Minimal local shim for essential HTMX behaviors used in the app.
// This is intentionally small: implements hx-get, hx-post and hx-delete
// by listening to clicks and submits and fetching content to replace targets.
// It is not a full HTMX implementation, but enough for page forms and lists.
(function(){
  if (window.htmx) return; // don't override real htmx if present
  window.htmx = { // minimal stub
    _version: 'local-shim',
    logAll: function(){ console.info('htmx.local: logging enabled'); }
  };

  function resolveTarget(selector){
    if (!selector) return null;
    try { return document.querySelector(selector); } catch(e){ return null; }
  }

  function fetchToTarget(url, options, targetSelector){
    var target = resolveTarget(targetSelector);
    return fetch(url, options)
      .then(function(resp){
        if (!resp.ok) throw new Error('Network response not ok: ' + resp.status);
        return resp.text();
      })
      .then(function(html){ if (target) target.innerHTML = html; return html; });
  }

  document.addEventListener('click', function(ev){
    var el = ev.target.closest && ev.target.closest('[hx-get],[hx-delete]');
    if (!el) return;
    try { ev.preventDefault(); } catch(e){}
    var url = el.getAttribute('hx-get') || el.getAttribute('hx-delete');
    var method = el.hasAttribute('hx-delete') ? 'DELETE' : 'GET';
    var target = el.getAttribute('hx-target');
    if (!url) return;
    fetchToTarget(url, { method: method, credentials: 'same-origin' }, target)
      .catch(function(err){ console.error('htmx.local fetch error', err, url); });
  }, true);

  document.addEventListener('submit', function(ev){
    var form = ev.target;
    if (!form || !form.hasAttribute('hx-post')) return;
    try { ev.preventDefault(); } catch(e){}
    var url = form.getAttribute('hx-post') || form.getAttribute('action');
    var target = form.getAttribute('hx-target');
    var enctype = form.enctype || '';
    var opts = { method: 'POST', credentials: 'same-origin' };
    if (enctype.indexOf('application/json') !== -1) {
      // fallback: read inputs into simple JSON
      var data = {};
      Array.prototype.slice.call(form.elements).forEach(function(el){
        if (!el.name) return;
        if (el.type === 'checkbox') data[el.name] = el.checked;
        else data[el.name] = el.value;
      });
      opts.body = JSON.stringify(data);
      opts.headers = { 'Content-Type': 'application/json' };
    } else {
      opts.body = new FormData(form);
    }
    fetchToTarget(url, opts, target)
      .catch(function(err){ console.error('htmx.local form submit error', err, url); });
  }, true);
})();
