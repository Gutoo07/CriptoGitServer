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

drop table usuario (
	id		int				not null,
	usuario	varchar(100)	not null,
	senha	varchar(100)	not null,
	chavePublica	varchar(50)	 null
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
	sha1		varchar(40)		not null,
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
-------------------------------------------------------------------------

go

select * from usuario
select * from commite
select * from repositorio
select * from blob
select * from arquivo
select * from diretorio

go

create procedure sp_commit(@opc char(1), @id int, @msg varchar(100), @usuarioAutor int, @commiteAnterior int, @saida varchar(100) output)
as
begin
	if (upper(@opc) = 'I')
	begin
		if (@id is not null and @usuarioAutor is not null)
		begin
			insert into commite values
			(@id, @msg, @usuarioAutor, @commiteAnterior)
			set @saida = 'Commit #'+cast(@id as varchar(10))+' inserido com sucesso.'
		end
		else
		begin
			raiserror('Erro ao Inserir Commit: um ou mais campos obrigatorios estao em branco', 16, 1)
		end
	end
	else
	begin
		raiserror('Erro em sp_commit: Opcao Invalida', 16, 1)
	end
end

