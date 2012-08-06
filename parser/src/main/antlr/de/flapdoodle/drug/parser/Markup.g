grammar Markup;

options
{
output=AST;
}

@header
{
package de.flapdoodle.drug.parser;

import de.flapdoodle.drug.markup.*;
}

@lexer::header
{
package de.flapdoodle.drug.parser;

import de.flapdoodle.drug.markup.*;
}

@rulecatch
{
catch (RecognitionException pex)
{
	throw pex;
}
}
/*
sample text
Das ist ein {[o:Text], den [s0:man] leicht [p:lesen] kann.} Und im zweiten Teil {[p:fehlt|fehlen] dem [s:Text] die [o1:Botschaft]}
Eine aus {[o:Ton] [p:formen|geformte] [o1:Vase] mit einem [Pinsel] blau [p1:anmalen]}
*/

markup	returns [Markup rmarkup]
	:	start {$rmarkup=new Markup($start.rlist);};

start	returns [List<IPart> rlist]
	:	{ $rlist=new ArrayList<IPart>(); }
	(any_text {$rlist.add($any_text.rtext);}
	|key  {$rlist.add($key.rkey);}
	|subject  {$rlist.add($subject.rpart);}
	|predicate {$rlist.add($predicate.rpart);}
	|object {$rlist.add($object.rpart);}
	|block {$rlist.add($block.rblock);}
	)+;

text	:	ID | WS | ANY_TEXT ;

block	returns [Block rblock]
	: BLOCK_OPEN any_part BLOCK_CLOSE {$rblock=new Block($any_part.rlist);};

any_part returns [List<IPart> rlist]
	:	{ $rlist=new ArrayList<IPart>(); }
	(any_text {$rlist.add($any_text.rtext);}
	|key  {$rlist.add($key.rkey);}
	|subject  {$rlist.add($subject.rpart);}
	|predicate {$rlist.add($predicate.rpart);}
	|object {$rlist.add($object.rpart);}
	)+
	;
	
any_text returns [Text rtext]
	:	text {$rtext = new Text($text.text);};
	
key	returns [Key rkey]
	:	TAG_OPEN label TAG_CLOSE {$rkey = new Key($label.rlabel);};

object	returns [RelationPart rpart]
	:	OBJECT_TAG index  label TAG_CLOSE {$rpart = new RelationPart(Type.Object,$index.rnr,$label.rlabel);};

subject	returns [RelationPart rpart]
	:	SUBJECT_TAG index  label TAG_CLOSE {$rpart = new RelationPart(Type.Subject,$index.rnr,$label.rlabel);};

predicate	returns [RelationPart rpart]
	:	PREDICATE_TAG index  label TAG_CLOSE {$rpart = new RelationPart(Type.Predicate,$index.rnr,$label.rlabel);};

index	returns [Integer rnr]
	:	index_number ':' { $rnr = Integer.valueOf($index_number.text); }
	| ':' { $rnr = 0; };

index_number
	:	IDX
	;

label 	returns [Label rlabel]
	:	single=single_label { $rlabel = new Label($single.text); }
	| vlabel=label_with_alt { $rlabel = $vlabel.rlabel; };

label_with_alt returns [Label rlabel]
	:	vlabel=single_label '|' valt=single_label { $rlabel = new Label($vlabel.text,$valt.text); }
		;

single_label	:	ID
	;


IDX 	:	'0'..'9';
ID  :	('a'..'z'|'A'..'Z') ('a'..'z'|'A'..'Z'|'_')*
    ;
ANY_TEXT:	','|'.';
WS  :   ( ' '
        | '\t'
        | '\r'
        | '\n'
        ) 
	;

BLOCK_OPEN
	:	'{'
	;

BLOCK_CLOSE
	:	'}'
	;
	
TAG_OPEN
	:	'['
	;
	
OBJECT_TAG
	:	TAG_OPEN 'o'
	;

SUBJECT_TAG
	:	TAG_OPEN 's'
	;

PREDICATE_TAG
	:	TAG_OPEN 'p'
	;

TAG_CLOSE
	:	']'
	;
