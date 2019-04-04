#Run   
Talvez você quereira gerar a apk desse app e o Android Studio de erro.
Nesse caso atualize app/build.gradle, certifique que eles tem a mesma versão e de preferência para a última
```txt
compileSdkVersion 28
implementation 'com.android.support:appcompat-v7:28.0.0'
targetSdkVersion 28
```

#DONE
- tirar estilo duplicados
- add logo
- adicionar botão de config
- logica dos pontos
- avisar quando esta de onze (dialogo)
- falar quem ganhou (dialogo)
- zerar pontos (dialogo)
- app nao pode ser rotacionado
- tela não apaga
- limitar tamanho dos nomes das equipes (9 caracteres)
- melhorar a logo
- na edicao do nome mostrar quantos caracteres pode digitar
- adicionar lifecycle
- melhorar codigo
- internacionalização (en, pt, es)
- liberar a edição do nome quando clicar em cima do nome

#TODO FUTURE
- mudar `holo color` do botão, botão normal (sem sobreescrever background é possivel ver quando é clicado)
    mas mesmo se eu mudar a cor do botão pelo tema a cor fica mais clara
    a cor que eu defini so fica visivel quando eu clico no botão.
    Seria melhor se tive um efeito contrario, ficar claro quando clica uma vez que o botão tem cor escura
    https://developer.android.com/guide/topics/ui/controls/button

#NOTA   
app suporta apenas truco paulista


## screenshot
![SomaTruco App](img/app.jpeg)
