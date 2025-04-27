create database TrabalhoLabEngSw
go
use TrabalhoLabEngSw
go
-------------
use master
go
drop database TrabalhoLabEngSw
go
create database TrabalhoLabEngSw
go
use TrabalhoLabEngSw
--------------

create table usuario (
	id		int				not null,
	usuario	varchar(100)	not null,
	senha	varchar(100)	not null,
	chavePrivada	varchar(50)	not null,
	chavePublica	varchar(50)	not null
	primary key (id)
)

go

create table repositorio (
	id		int			not null,
	nome	varchar(50)	not null,
	chave	varchar(50)	not null,
	usuarioDonoId	int	not null
	primary key(id)
	foreign key(usuarioDonoId) references usuario(id)
)

go

create table commite (
	id					int			not null,
	msg				varchar(100)	not null,
	usuarioAutor	int				not null,
	commiteAnterior	int				null
	primary key(id)
	foreign key(usuarioAutor) references usuario(id),
	foreign key(commiteAnterior) references commite(id)
)

go

create table diretorio(
	id				int			not null,
	nome		varchar(100)	not null,
	commiteId		int			not null,
	diretorioPaiId	int			null
	primary key(id)
	foreign key(commiteId) references commite(id),
	foreign key(diretorioPaiId) references diretorio(id)
)

go

create table blob (
	sha1		int		not null,
	blob varbinary(max)	not null
	primary key (sha1)
)

go

create table arquivo(
	id			varchar(40)		not null,
	nome		varchar(100)	not null,
	blobSha1		int				not null,
	diretorioId	int				not null
	primary key(id)
	foreign key(blobSha1) references blob(sha1),
	foreign key(diretorioId) references diretorio(id)
)

go

create table usuario_repositorio (
	usuarioId		int		not null,
	repositorioId	int		not null
	primary key(usuarioId, repositorioId)
	foreign key(usuarioId) references usuario(id),
	foreign key(repositorioId) references repositorio(id)
)

