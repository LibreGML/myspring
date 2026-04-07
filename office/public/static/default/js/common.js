$('.public-header-right-m-nav-btn[data-title="user"]').click(function (){
    $('.public-header').addClass('hover-user');
    $('body').css('overflow','hidden');
    $('.public-header-right-m-nav-btn[data-title="menu"] i').addClass('toggle-animate');
})
$('.public-header-right-m-nav-btn[data-title="menu"]').click(function (){
    if ($('.public-header').hasClass('hover-menu')) {
        $('body').css('overflow','');
        $('.public-header').removeClass('hover-menu');
        $(this).find('i').removeClass('toggle-animate');
    } else if ($('.public-header').hasClass('hover-user')) {
        $('body').css('overflow','');
        $('.public-header').removeClass('hover-user');
        $(this).find('i').removeClass('toggle-animate');
    } else {
        $('.public-header').addClass('hover-menu');
        $('body').css('overflow','hidden');
        $(this).find('i').addClass('toggle-animate');
    }

})

$('.public-footer .entrance .item .title').click(function (){
    if ($(this).parent('.item').hasClass('hover')) {
        $(this).parent('.item').removeClass('hover');
    } else {
        $(this).parent('.item').addClass('hover');
    }
})