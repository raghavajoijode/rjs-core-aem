(this["webpackJsonprjs-core"]=this["webpackJsonprjs-core"]||[]).push([[0],{209:function(t,e,n){t.exports=n(497)},494:function(t,e,n){},495:function(t,e,n){},496:function(t,e,n){},497:function(t,e,n){"use strict";n.r(e);n(210),n(407),n(455);var r=n(59),a=n(38),o=n(3),c=n.n(o),i=n(204),s=n(498),p=n(48),u=n(49),h=n(51),l=n(50),m=n(20),O=function(t){Object(h.a)(n,t);var e=Object(l.a)(n);function n(){return Object(p.a)(this,n),e.apply(this,arguments)}return Object(u.a)(n,[{key:"render",value:function(){return c.a.createElement("div",null,this.childComponents,this.childPages)}}]),n}(m.Page),b=Object(m.withModel)(O),j=n(207),d=n(58);n(494);var f,C,y=function(t){Object(h.a)(n,t);var e=Object(l.a)(n);function n(){return Object(p.a)(this,n),e.apply(this,arguments)}return Object(u.a)(n,[{key:"containerProps",get:function(){var t=Object(j.a)(Object(d.a)(n.prototype),"containerProps",this);return t.className=(t.className||"")+" page "+(this.props.cssClassNames||""),t}}]),n}(m.Page),g=(Object(m.MapTo)("rjs-core/components/page")(Object(m.withComponentMappingContext)((f=y,function(t){Object(h.a)(n,t);var e=Object(l.a)(n);function n(){return Object(p.a)(this,n),e.apply(this,arguments)}return Object(u.a)(n,[{key:"render",value:function(){var t=this,e=this.props.cqPath;return e?(C=C||"html",c.a.createElement(s.a,{key:e,exact:!0,path:"(.*)"+e+"(."+C+")?",render:function(e){return c.a.createElement(f,Object.assign({},t.props,e))}})):c.a.createElement(f,this.props)}}]),n}(o.Component)))),n(206)),v=n.n(g);n(495);var x=function(t){Object(h.a)(n,t);var e=Object(l.a)(n);function n(){return Object(p.a)(this,n),e.apply(this,arguments)}return Object(u.a)(n,[{key:"richTextContent",get:function(){return c.a.createElement("div",{id:(t=this.props.cqPath,t&&t.replace(/\/|:/g,"_")),"data-rte-editelement":!0,dangerouslySetInnerHTML:{__html:v.a.sanitize(this.props.text)}});var t}},{key:"textContent",get:function(){return c.a.createElement("div",null,this.props.text)}},{key:"render",value:function(){return this.props.richText?this.richTextContent:this.textContent}}]),n}(o.Component);Object(m.MapTo)("rjs-core/components/text")(x,{emptyLabel:"Text",isEmpty:function(t){return!t||!t.text||t.text.trim().length<1}});Object(m.MapTo)("rjs-core/components/container")(Object(m.withComponentMappingContext)(m.AllowedComponentsContainer),{emptyLabel:"Container",isEmpty:function(t){return!t||!t.cqItemsOrder||0===t.cqItemsOrder.length}});Object(m.MapTo)("rjs-core/components/experiencefragment")(Object(m.withComponentMappingContext)(m.Container),{emptyLabel:"Experience Fragment",isEmpty:function(t){return!t||!t.configured}});n(496);document.addEventListener("DOMContentLoaded",(function(){r.ModelManager.initialize().then((function(t){var e=Object(a.a)();Object(i.render)(c.a.createElement(s.b,{history:e},c.a.createElement(b,{history:e,cqChildren:t[r.Constants.CHILDREN_PROP],cqItems:t[r.Constants.ITEMS_PROP],cqItemsOrder:t[r.Constants.ITEMS_ORDER_PROP],cqPath:t[r.Constants.PATH_PROP],locationPathname:window.location.pathname})),document.getElementById("spa-root"))}))}))}},[[209,1,2]]]);
//# sourceMappingURL=main.8dcfec74.chunk.js.map