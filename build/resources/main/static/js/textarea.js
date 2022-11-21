function resize(obj) {
    obj.style.height = '1px';
    obj.style.height = obj.scrollHeight + 'px';
/*    obj.style.height = (12 + obj.scrollHeight) + 'px';*/
}

function reset(obj) {
    obj.style.height = '1px';
    obj.style.height = 5 + 'rem';
}