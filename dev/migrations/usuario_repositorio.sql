create table usuario_repositorio (
    usuario_id bigint not null,
    repositorio_id bigint not null,
    primary key (usuario_id, repositorio_id),
    foreign key (usuario_id) references usuario(id),
    foreign key (repositorio_id) references repositorio(id)
)