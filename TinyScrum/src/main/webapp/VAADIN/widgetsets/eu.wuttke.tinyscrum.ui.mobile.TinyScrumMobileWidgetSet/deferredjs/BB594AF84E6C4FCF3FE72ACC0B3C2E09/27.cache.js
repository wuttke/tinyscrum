function gQ(){}
function bQ(){}
function D2(){}
function s4(){}
function lvb(){}
function kvb(){}
function h5b(){}
function x5b(){}
function B5b(){}
function F5b(){}
function J5b(){}
function y5b(b){this.b=b}
function C5b(b){this.b=b}
function G5b(b){$();this.b=b}
function v5b(b,c){b.enctype=c;b.encoding=c}
function Mzb(b,c){b.onload=function(){c.Sf()}}
function j5b(b){vBb(b.n,false);b.o||(b.e.Kb[yoc]=true,undefined);b.d=false}
function k5b(b){vBb(b.n,true);b.e.Kb[yoc]=false;b.d=true;if(b.o){i5b(b);b.o=false}}
function i5b(b){if(b.p){$doc.body.removeChild(b.p);b.p.onload=null;b.p=null}}
function t4(){var b;this.Kb=(b=$doc.createElement(Doc),b.type=Noc,b)}
function K5b(b){var c;this.b=b;this.Kb=(c=$doc.createElement(Doc),c.type='file',c);this.Kb[ooc]='gwt-FileUpload'}
function n5b(b,c){if(b.f!=c){b.f=c;if(b.f){O$(b.e,1024);O$(b.e,2048)}}y$(b.Kb,'v-upload-immediate',b.f)}
function m5b(b){o_(b.k,b.n);o_(b.k,b.e);b.e=new K5b(b);b.e.Kb.name=b.j+jxc;b.e.Kb[yoc]=!b.d;o3(b.k,b.e);o3(b.k,b.n);b.f&&O$(b.e,1024)}
function iQ(){eQ=new gQ;Sb((Qb(),Pb),27);!!$stats&&$stats(vc(ixc,Smc,-1,-1));eQ.Kc();!!$stats&&$stats(vc(ixc,Iuc,-1,-1))}
function fQ(){var b,c,d;while(cQ){d=lb;cQ=cQ.b;!cQ&&(dQ=null);if(!d){(Wsb(),Vsb).qg(UF,new lvb);ykb()}else{try{(Wsb(),Vsb).qg(UF,new lvb);ykb()}catch(b){b=$I(b);if(vr(b,38)){c=b;qqb.Ae(c)}else throw b}}}}
function o5b(b){if(b.e.Kb.value.length==0||b.o||!b.d){qqb.Ce('Submit cancelled (disabled, no file or already submitted)');return}Uhb(b.b);b.c.submit();b.o=true;qqb.Ce('Submitted form');j5b(b);b.q=new G5b(b);bb(b.q,800)}
function l5b(b){var c;if(!b.p){c=$doc.createElement(Loc);c.innerHTML="<iframe src=\"javascript:''\" name='"+b.j+"_TGT_FRAME' style='position:absolute;width:0;height:0;border:0'>"||Nmc;b.p=ge(c);$doc.body.appendChild(b.p);b.c.target=b.j+'_TGT_FRAME';Mzb(b.p,b)}}
function p5b(){this.Kb=$doc.createElement('form');this.e=new K5b(this);this.k=new r3;this.g=new t4;this.c=this.Kb;v5b(this.Kb,dxc);this.c.method='post';B1(this,this.k);o3(this.k,this.g);o3(this.k,this.e);this.n=new xBb;F$(this.n,new y5b(this),(Ak(),Ak(),zk));o3(this.k,this.n);this.Kb[ooc]='v-upload';this.Hb==-1?mY(this.Kb,241|(this.Kb.__eventBits||0)):(this.Hb|=241)}
var jxc='_file',kxc='buttoncaption',ixc='runCallbacks27';_=gQ.prototype=bQ.prototype=new J;_.gC=function hQ(){return kv};_.Kc=function lQ(){fQ()};_.cM={};_=D2.prototype=new c$;_.gC=function E2(){return ex};_.Pc=function F2(b){J$(this,b)};_.cM={10:1,13:1,15:1,22:1,70:1,71:1};_=t4.prototype=s4.prototype=new c$;_.gC=function u4(){return tx};_.cM={10:1,13:1,15:1,22:1,70:1,71:1};_=lvb.prototype=kvb.prototype=new J;_.Ne=function mvb(){return new p5b};_.gC=function nvb(){return aB};_.cM={139:1};_=p5b.prototype=h5b.prototype=new y1;_.gC=function q5b(){return UF};_.fd=function r5b(){I$(this);!!this.b&&l5b(this)};_.Pc=function s5b(b){(mZ(b.type)&241)>0&&(Zrb(this.b.H,b,this,null),undefined);J$(this,b)};_.gd=function t5b(){K$(this);this.o||i5b(this)};_.Sf=function u5b(){Qrb((Ec(),Dc),new C5b(this))};_._d=function w5b(b,c){var d;if(aib(c,this,b,true)){return}if('notStarted' in b[1]){bb(this.q,400);return}if('forceSubmit' in b[1]){o5b(this);return}n5b(this,Boolean(b[1][Jpc]));this.b=c;this.j=b[1][Hoc];this.i=b[1]['nextid'];d=Zhb(c,b[1][Mpc][ktc]);this.c.action=d;if(kxc in b[1]){this.n.c.textContent=b[1][kxc]||Nmc;this.n.Kb.style.display=Nmc}else{this.n.Kb.style.display=mnc}this.e.Kb.name=this.j+jxc;if(yoc in b[1]||Hpc in b[1]){j5b(this)}else if(!Boolean(b[1][_pc])){k5b(this);l5b(this)}};_.cM={10:1,13:1,15:1,17:1,19:1,20:1,21:1,22:1,26:1,34:1,70:1,71:1,76:1,77:1};_.b=null;_.c=null;_.d=true;_.f=false;_.i=0;_.j=null;_.n=null;_.o=false;_.p=null;_.q=null;_=y5b.prototype=x5b.prototype=new J;_.gC=function z5b(){return QF};_.fc=function A5b(b){this.b.f?(this.b.e.Kb.click(),undefined):o5b(this.b)};_.cM={12:1,40:1};_.b=null;_=C5b.prototype=B5b.prototype=new J;_.Tb=function D5b(){if(this.b.o){if(this.b.b){!!this.b.q&&ab(this.b.q);qqb.Ce('VUpload:Submit complete');Uhb(this.b.b)}m5b(this.b);this.b.o=false;k5b(this.b);this.b.Gb||i5b(this.b)}};_.gC=function E5b(){return RF};_.cM={3:1,14:1};_.b=null;_=G5b.prototype=F5b.prototype=new Y;_.gC=function H5b(){return SF};_.Pb=function I5b(){qqb.Ce('Visiting server to see if upload started event changed UI.');shb(this.b.b,this.b.j,'pollForStart',Nmc+this.b.i,true,105)};_.cM={66:1};_.b=null;_=K5b.prototype=J5b.prototype=new D2;_.gC=function L5b(){return TF};_.Pc=function M5b(b){J$(this,b);if(mZ(b.type)==1024){this.b.f&&this.b.e.Kb.value!=null&&!Cec(Nmc,this.b.e.Kb.value)&&o5b(this.b)}else if((Jlb(),!Ilb&&(Ilb=new bmb),Jlb(),Ilb).b.i&&mZ(b.type)==2048){this.b.e.Kb.click();this.b.e.Kb.blur()}};_.cM={10:1,13:1,15:1,22:1,70:1,71:1};_.b=null;var kv=hdc(tuc,'AsyncLoader27'),ex=hdc(wuc,'FileUpload'),tx=hdc(wuc,'Hidden'),aB=hdc(Duc,'WidgetMapImpl$37$1'),QF=hdc(Cuc,'VUpload$1'),RF=hdc(Cuc,'VUpload$2'),SF=hdc(Cuc,'VUpload$3'),TF=hdc(Cuc,'VUpload$MyFileUpload');Jmc(iQ)();