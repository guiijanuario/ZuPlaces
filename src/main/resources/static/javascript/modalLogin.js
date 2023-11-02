const modal = document.getElementById('modal-login');
const iconLogin = document.querySelector('.divMinhaConta > a > img');
const iconLoginMobile = document.querySelector('.div-mobile-menu > .divMinhaConta > a > img');
const fecharModalIcon = document.querySelector('.fechar');

function iniciaModal(modalID) {
    modal.classList.add('mostrar')
}

function fechaModal(modalID) {
    modal.classList.remove('mostrar')
}

iconLogin.addEventListener('click', function(){
    iniciaModal('modal-login')
})

iconLoginMobile.addEventListener('click', function(){
    iniciaModal('modal-login')
})

fecharModalIcon.addEventListener('click', function(){
    fechaModal('modal-login')
})





