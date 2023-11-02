function menuShow() {
    let menuMobile = document.querySelector('.mobile-menu');
    let loginMobile = document.querySelector('.divMinhaConta');
    let carrinhoMobile = document.querySelector('.divCarrinho');
    if (menuMobile.classList.contains('open') && loginMobile.classList.contains('some') && carrinhoMobile.classList.contains('some')) {
        menuMobile.classList.remove('open');
        loginMobile.classList.remove('some');
        carrinhoMobile.classList.remove('some');
        document.querySelector('.icon').src = "./imagens/menu_white_36dp.svg";
    } else {
        menuMobile.classList.add('open');
        loginMobile.classList.add('some');
        carrinhoMobile.classList.add('some');
        document.querySelector('.icon').src = "./imagens/close_white_36dp.svg";
    }
}

function menuShowFiltro() {
    let menuMobile = document.querySelector('.mobile-filtro');
    if (menuMobile.classList.contains('open')) {
        menuMobile.classList.remove('open');
        document.querySelector('.icon').src = "./imagens/menu_white_36dp.svg";
    } else {
        menuMobile.classList.add('open');
        document.querySelector('.icon').src = "./imagens/close_white_36dp.svg";
    }
}
