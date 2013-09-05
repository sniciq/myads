var MooTools = {
    version: "1.2.4",
    build: "0d9113241a90b9cd5643b926795852a2026710d4"
};
var Native = function(k) {
    k = k || {};
    var a = k.name;
    var i = k.legacy;
    var b = k.protect;
    var c = k.implement;
    var h = k.generics;
    var f = k.initialize;
    var g = k.afterImplement ||
    function() {};
    var d = f || i;
    h = h !== false;
    d.constructor = Native;
    d.$family = {
        name: "native"
    };
    if (i && f) {
        d.prototype = i.prototype
    }
    d.prototype.constructor = d;
    if (a) {
        var e = a.toLowerCase();
        d.prototype.$family = {
            name: e
        };
        Native.typize(d, e)
    }
    var j = function(n, l, o, m) {
        if (!b || m || !n.prototype[l]) {
            n.prototype[l] = o
        }
        if (h) {
            Native.genericize(n, l, b)
        }
        g.call(n, l, o);
        return n
    };
    d.alias = function(n, l, p) {
        if (typeof n == "string") {
            var o = this.prototype[n];
            if ((n = o)) {
                return j(this, l, n, p)
            }
        }
        for (var m in n) {
            this.alias(m, n[m], l)
        }
        return this
    };
    d.implement = function(m, l, o) {
        if (typeof m == "string") {
            return j(this, m, l, o)
        }
        for (var n in m) {
            j(this, n, m[n], l)
        }
        return this
    };
    if (c) {
        d.implement(c)
    }
    return d
};
Native.genericize = function(b, c, a) {
    if ((!a || !b[c]) && typeof b.prototype[c] == "function") {
        b[c] = function() {
            var d = Array.prototype.slice.call(arguments);
            return b.prototype[c].apply(d.shift(), d)
        }
    }
};
Native.implement = function(d, c) {
    for (var b = 0,
    a = d.length; b < a; b++) {
        d[b].implement(c)
    }
};
Native.typize = function(a, b) {
    if (!a.type) {
        a.type = function(c) {
            return ($type(c) === b)
        }
    }
}; (function() {
    var a = {
        Array: Array,
        Date: Date,
        Function: Function,
        Number: Number,
        RegExp: RegExp,
        String: String
    };
    for (var h in a) {
        new Native({
            name: h,
            initialize: a[h],
            protect: true
        })
    }
    var d = {
        "boolean": Boolean,
        "native": Native,
        object: Object
    };
    for (var c in d) {
        Native.typize(d[c], c)
    }
    var f = {
        Array: ["concat", "indexOf", "join", "lastIndexOf", "pop", "push", "reverse", "shift", "slice", "sort", "splice", "toString", "unshift", "valueOf"],
        String: ["charAt", "charCodeAt", "concat", "indexOf", "lastIndexOf", "match", "replace", "search", "slice", "split", "substr", "substring", "toLowerCase", "toUpperCase", "valueOf"]
    };
    for (var e in f) {
        for (var b = f[e].length; b--;) {
            Native.genericize(a[e], f[e][b], true)
        }
    }
})();
var Hash = new Native({
    name: "Hash",
    initialize: function(a) {
        if ($type(a) == "hash") {
            a = $unlink(a.getClean())
        }
        for (var b in a) {
            this[b] = a[b]
        }
        return this
    }
});
Hash.implement({
    forEach: function(b, c) {
        for (var a in this) {
            if (this.hasOwnProperty(a)) {
                b.call(c, this[a], a, this)
            }
        }
    },
    getClean: function() {
        var b = {};
        for (var a in this) {
            if (this.hasOwnProperty(a)) {
                b[a] = this[a]
            }
        }
        return b
    },
    getLength: function() {
        var b = 0;
        for (var a in this) {
            if (this.hasOwnProperty(a)) {
                b++
            }
        }
        return b
    }
});
Hash.alias("forEach", "each");
Array.implement({
    forEach: function(c, d) {
        for (var b = 0,
        a = this.length; b < a; b++) {
            c.call(d, this[b], b, this)
        }
    }
});
Array.alias("forEach", "each");
function $A(b) {
    if (b.item) {
        var a = b.length,
        c = new Array(a);
        while (a--) {
            c[a] = b[a]
        }
        return c
    }
    return Array.prototype.slice.call(b)
}
function $arguments(a) {
    return function() {
        return arguments[a]
    }
}
function $chk(a) {
    return !! (a || a === 0)
}
function $clear(a) {
    clearTimeout(a);
    clearInterval(a);
    return null
}
function $defined(a) {
    return (a != undefined)
}
function $each(c, b, d) {
    var a = $type(c); ((a == "arguments" || a == "collection" || a == "array") ? Array: Hash).each(c, b, d)
}
function $empty() {}
function $extend(c, a) {
    for (var b in (a || {})) {
        c[b] = a[b]
    }
    return c
}
function $H(a) {
    return new Hash(a)
}
function $lambda(a) {
    return ($type(a) == "function") ? a: function() {
        return a
    }
}
function $merge() {
    var a = Array.slice(arguments);
    a.unshift({});
    return $mixin.apply(null, a)
}
function $mixin(e) {
    for (var d = 1,
    a = arguments.length; d < a; d++) {
        var b = arguments[d];
        if ($type(b) != "object") {
            continue
        }
        for (var c in b) {
            var g = b[c],
            f = e[c];
            e[c] = (f && $type(g) == "object" && $type(f) == "object") ? $mixin(f, g) : $unlink(g)
        }
    }
    return e
}
function $pick() {
    for (var b = 0,
    a = arguments.length; b < a; b++) {
        if (arguments[b] != undefined) {
            return arguments[b]
        }
    }
    return null
}
function $random(b, a) {
    return Math.floor(Math.random() * (a - b + 1) + b)
}
function $splat(b) {
    var a = $type(b);
    return (a) ? ((a != "array" && a != "arguments") ? [b] : b) : []
}
var $time = Date.now ||
function() {
    return + new Date
};
function $try() {
    for (var b = 0,
    a = arguments.length; b < a; b++) {
        try {
            return arguments[b]()
        } catch(c) {}
    }
    return null
}
function $type(a) {
    if (a == undefined) {
        return false
    }
    if (a.$family) {
        return (a.$family.name == "number" && !isFinite(a)) ? false: a.$family.name
    }
    if (a.nodeName) {
        switch (a.nodeType) {
        case 1:
            return "element";
        case 3:
            return (/\S/).test(a.nodeValue) ? "textnode": "whitespace"
        }
    } else {
        if (typeof a.length == "number") {
            if (a.callee) {
                return "arguments"
            } else {
                if (a.item) {
                    return "collection"
                }
            }
        }
    }
    return typeof a
}
function $unlink(c) {
    var b;
    switch ($type(c)) {
    case "object":
        b = {};
        for (var e in c) {
            b[e] = $unlink(c[e])
        }
        break;
    case "hash":
        b = new Hash(c);
        break;
    case "array":
        b = [];
        for (var d = 0,
        a = c.length; d < a; d++) {
            b[d] = $unlink(c[d])
        }
        break;
    default:
        return c
    }
    return b
}
var Browser = $merge({
    Engine: {
        name: "unknown",
        version: 0
    },
    Platform: {
        name: (window.orientation != undefined) ? "ipod": (navigator.platform.match(/mac|win|linux/i) || ["other"])[0].toLowerCase()
    },
    Features: {
        xpath: !!(document.evaluate),
        air: !!(window.runtime),
        query: !!(document.querySelector)
    },
    Plugins: {},
    Engines: {
        presto: function() {
            return (!window.opera) ? false: ((arguments.callee.caller) ? 960 : ((document.getElementsByClassName) ? 950 : 925))
        },
        trident: function() {
            return (!window.ActiveXObject) ? false: ((window.XMLHttpRequest) ? ((document.querySelectorAll) ? 6 : 5) : 4)
        },
        webkit: function() {
            return (navigator.taintEnabled) ? false: ((Browser.Features.xpath) ? ((Browser.Features.query) ? 525 : 420) : 419)
        },
        gecko: function() {
            return (!document.getBoxObjectFor && window.mozInnerScreenX == null) ? false: ((document.getElementsByClassName) ? 19 : 18)
        }
    }
},
Browser || {});
Browser.Platform[Browser.Platform.name] = true;
Browser.detect = function() {
    for (var b in this.Engines) {
        var a = this.Engines[b]();
        if (a) {
            this.Engine = {
                name: b,
                version: a
            };
            this.Engine[b] = this.Engine[b + a] = true;
            break
        }
    }
    return {
        name: b,
        version: a
    }
};
Browser.detect();
Browser.Request = function() {
    return $try(function() {
        return new XMLHttpRequest()
    },
    function() {
        return new ActiveXObject("MSXML2.XMLHTTP")
    },
    function() {
        return new ActiveXObject("Microsoft.XMLHTTP")
    })
};
Browser.Features.xhr = !!(Browser.Request());
Browser.Plugins.Flash = (function() {
    var a = ($try(function() {
        return navigator.plugins["Shockwave Flash"].description
    },
    function() {
        return new ActiveXObject("ShockwaveFlash.ShockwaveFlash").GetVariable("$version")
    }) || "0 r0").match(/\d+/g);
    return {
        version: parseInt(a[0] || 0 + "." + a[1], 10) || 0,
        build: parseInt(a[2], 10) || 0
    }
})();
function $exec(b) {
    if (!b) {
        return b
    }
    if (window.execScript) {
        window.execScript(b)
    } else {
        var a = document.createElement("script");
        a.setAttribute("type", "text/javascript");
        a[(Browser.Engine.webkit && Browser.Engine.version < 420) ? "innerText": "text"] = b;
        document.head.appendChild(a);
        document.head.removeChild(a)
    }
    return b
}
Native.UID = 1;
var $uid = (Browser.Engine.trident) ?
function(a) {
    return (a.uid || (a.uid = [Native.UID++]))[0]
}: function(a) {
    return a.uid || (a.uid = Native.UID++)
};
var Window = new Native({
    name: "Window",
    legacy: (Browser.Engine.trident) ? null: window.Window,
    initialize: function(a) {
        $uid(a);
        if (!a.Element) {
            a.Element = $empty;
            if (Browser.Engine.webkit) {
                a.document.createElement("iframe")
            }
            a.Element.prototype = (Browser.Engine.webkit) ? window["[[DOMElement.prototype]]"] : {}
        }
        a.document.window = a;
        return $extend(a, Window.Prototype)
    },
    afterImplement: function(b, a) {
        window[b] = Window.Prototype[b] = a
    }
});
Window.Prototype = {
    $family: {
        name: "window"
    }
};
new Window(window);
var Document = new Native({
    name: "Document",
    legacy: (Browser.Engine.trident) ? null: window.Document,
    initialize: function(a) {
        $uid(a);
        a.head = a.getElementsByTagName("head")[0];
        a.html = a.getElementsByTagName("html")[0];
        if (Browser.Engine.trident && Browser.Engine.version <= 4) {
            $try(function() {
                a.execCommand("BackgroundImageCache", false, true)
            })
        }
        if (Browser.Engine.trident) {
            a.window.attachEvent("onunload",
            function() {
                a.window.detachEvent("onunload", arguments.callee);
                a.head = a.html = a.window = null
            })
        }
        return $extend(a, Document.Prototype)
    },
    afterImplement: function(b, a) {
        document[b] = Document.Prototype[b] = a
    }
});
Document.Prototype = {
    $family: {
        name: "document"
    }
};
new Document(document);
Array.implement({
    every: function(c, d) {
        for (var b = 0,
        a = this.length; b < a; b++) {
            if (!c.call(d, this[b], b, this)) {
                return false
            }
        }
        return true
    },
    filter: function(d, e) {
        var c = [];
        for (var b = 0,
        a = this.length; b < a; b++) {
            if (d.call(e, this[b], b, this)) {
                c.push(this[b])
            }
        }
        return c
    },
    clean: function() {
        return this.filter($defined)
    },
    indexOf: function(c, d) {
        var a = this.length;
        for (var b = (d < 0) ? Math.max(0, a + d) : d || 0; b < a; b++) {
            if (this[b] === c) {
                return b
            }
        }
        return - 1
    },
    map: function(d, e) {
        var c = [];
        for (var b = 0,
        a = this.length; b < a; b++) {
            c[b] = d.call(e, this[b], b, this)
        }
        return c
    },
    some: function(c, d) {
        for (var b = 0,
        a = this.length; b < a; b++) {
            if (c.call(d, this[b], b, this)) {
                return true
            }
        }
        return false
    },
    associate: function(c) {
        var d = {},
        b = Math.min(this.length, c.length);
        for (var a = 0; a < b; a++) {
            d[c[a]] = this[a]
        }
        return d
    },
    link: function(c) {
        var a = {};
        for (var e = 0,
        b = this.length; e < b; e++) {
            for (var d in c) {
                if (c[d](this[e])) {
                    a[d] = this[e];
                    delete c[d];
                    break
                }
            }
        }
        return a
    },
    contains: function(a, b) {
        return this.indexOf(a, b) != -1
    },
    extend: function(c) {
        for (var b = 0,
        a = c.length; b < a; b++) {
            this.push(c[b])
        }
        return this
    },
    getLast: function() {
        return (this.length) ? this[this.length - 1] : null
    },
    getRandom: function() {
        return (this.length) ? this[$random(0, this.length - 1)] : null
    },
    include: function(a) {
        if (!this.contains(a)) {
            this.push(a)
        }
        return this
    },
    combine: function(c) {
        for (var b = 0,
        a = c.length; b < a; b++) {
            this.include(c[b])
        }
        return this
    },
    erase: function(b) {
        for (var a = this.length; a--; a) {
            if (this[a] === b) {
                this.splice(a, 1)
            }
        }
        return this
    },
    empty: function() {
        this.length = 0;
        return this
    },
    flatten: function() {
        var d = [];
        for (var b = 0,
        a = this.length; b < a; b++) {
            var c = $type(this[b]);
            if (!c) {
                continue
            }
            d = d.concat((c == "array" || c == "collection" || c == "arguments") ? Array.flatten(this[b]) : this[b])
        }
        return d
    },
    hexToRgb: function(b) {
        if (this.length != 3) {
            return null
        }
        var a = this.map(function(c) {
            if (c.length == 1) {
                c += c
            }
            return c.toInt(16)
        });
        return (b) ? a: "rgb(" + a + ")"
    },
    rgbToHex: function(d) {
        if (this.length < 3) {
            return null
        }
        if (this.length == 4 && this[3] == 0 && !d) {
            return "transparent"
        }
        var b = [];
        for (var a = 0; a < 3; a++) {
            var c = (this[a] - 0).toString(16);
            b.push((c.length == 1) ? "0" + c: c)
        }
        return (d) ? b: "#" + b.join("")
    }
});
Function.implement({
    extend: function(a) {
        for (var b in a) {
            this[b] = a[b]
        }
        return this
    },
    create: function(b) {
        var a = this;
        b = b || {};
        return function(d) {
            var c = b.arguments;
            c = (c != undefined) ? $splat(c) : Array.slice(arguments, (b.event) ? 1 : 0);
            if (b.event) {
                c = [d || window.event].extend(c)
            }
            var e = function() {
                return a.apply(b.bind || null, c)
            };
            if (b.delay) {
                return setTimeout(e, b.delay)
            }
            if (b.periodical) {
                return setInterval(e, b.periodical)
            }
            if (b.attempt) {
                return $try(e)
            }
            return e()
        }
    },
    run: function(a, b) {
        return this.apply(b, $splat(a))
    },
    pass: function(a, b) {
        return this.create({
            bind: b,
            arguments: a
        })
    },
    bind: function(b, a) {
        return this.create({
            bind: b,
            arguments: a
        })
    },
    bindWithEvent: function(b, a) {
        return this.create({
            bind: b,
            arguments: a,
            event: true
        })
    },
    attempt: function(a, b) {
        return this.create({
            bind: b,
            arguments: a,
            attempt: true
        })()
    },
    delay: function(b, c, a) {
        return this.create({
            bind: c,
            arguments: a,
            delay: b
        })()
    },
    periodical: function(c, b, a) {
        return this.create({
            bind: b,
            arguments: a,
            periodical: c
        })()
    }
});
Number.implement({
    limit: function(b, a) {
        return Math.min(a, Math.max(b, this))
    },
    round: function(a) {
        a = Math.pow(10, a || 0);
        return Math.round(this * a) / a
    },
    times: function(b, c) {
        for (var a = 0; a < this; a++) {
            b.call(c, a, this)
        }
    },
    toFloat: function() {
        return parseFloat(this)
    },
    toInt: function(a) {
        return parseInt(this, a || 10)
    }
});
Number.alias("times", "each"); (function(b) {
    var a = {};
    b.each(function(c) {
        if (!Number[c]) {
            a[c] = function() {
                return Math[c].apply(null, [this].concat($A(arguments)))
            }
        }
    });
    Number.implement(a)
})(["abs", "acos", "asin", "atan", "atan2", "ceil", "cos", "exp", "floor", "log", "max", "min", "pow", "sin", "sqrt", "tan"]);
String.implement({
    test: function(a, b) {
        return ((typeof a == "string") ? new RegExp(a, b) : a).test(this)
    },
    contains: function(a, b) {
        return (b) ? (b + this + b).indexOf(b + a + b) > -1 : this.indexOf(a) > -1
    },
    trim: function() {
        return this.replace(/^\s+|\s+$/g, "")
    },
    clean: function() {
        return this.replace(/\s+/g, " ").trim()
    },
    camelCase: function() {
        return this.replace(/-\D/g,
        function(a) {
            return a.charAt(1).toUpperCase()
        })
    },
    hyphenate: function() {
        return this.replace(/[A-Z]/g,
        function(a) {
            return ("-" + a.charAt(0).toLowerCase())
        })
    },
    capitalize: function() {
        return this.replace(/\b[a-z]/g,
        function(a) {
            return a.toUpperCase()
        })
    },
    escapeRegExp: function() {
        return this.replace(/([-.*+?^${}()|[\]\/\\])/g, "\\$1")
    },
    toInt: function(a) {
        return parseInt(this, a || 10)
    },
    toFloat: function() {
        return parseFloat(this)
    },
    hexToRgb: function(b) {
        var a = this.match(/^#?(\w{1,2})(\w{1,2})(\w{1,2})$/);
        return (a) ? a.slice(1).hexToRgb(b) : null
    },
    rgbToHex: function(b) {
        var a = this.match(/\d{1,3}/g);
        return (a) ? a.rgbToHex(b) : null
    },
    stripScripts: function(b) {
        var a = "";
        var c = this.replace(/<script[^>]*>([\s\S]*?)<\/script>/gi,
        function() {
            a += arguments[1] + "\n";
            return ""
        });
        if (b === true) {
            $exec(a)
        } else {
            if ($type(b) == "function") {
                b(a, c)
            }
        }
        return c
    },
    substitute: function(a, b) {
        return this.replace(b || (/\\?\{([^{}]+)\}/g),
        function(d, c) {
            if (d.charAt(0) == "\\") {
                return d.slice(1)
            }
            return (a[c] != undefined) ? a[c] : ""
        })
    }
});
Hash.implement({
    has: Object.prototype.hasOwnProperty,
    keyOf: function(b) {
        for (var a in this) {
            if (this.hasOwnProperty(a) && this[a] === b) {
                return a
            }
        }
        return null
    },
    hasValue: function(a) {
        return (Hash.keyOf(this, a) !== null)
    },
    extend: function(a) {
        Hash.each(a || {},
        function(c, b) {
            Hash.set(this, b, c)
        },
        this);
        return this
    },
    combine: function(a) {
        Hash.each(a || {},
        function(c, b) {
            Hash.include(this, b, c)
        },
        this);
        return this
    },
    erase: function(a) {
        if (this.hasOwnProperty(a)) {
            delete this[a]
        }
        return this
    },
    get: function(a) {
        return (this.hasOwnProperty(a)) ? this[a] : null
    },
    set: function(a, b) {
        if (!this[a] || this.hasOwnProperty(a)) {
            this[a] = b
        }
        return this
    },
    empty: function() {
        Hash.each(this,
        function(b, a) {
            delete this[a]
        },
        this);
        return this
    },
    include: function(a, b) {
        if (this[a] == undefined) {
            this[a] = b
        }
        return this
    },
    map: function(b, c) {
        var a = new Hash;
        Hash.each(this,
        function(e, d) {
            a.set(d, b.call(c, e, d, this))
        },
        this);
        return a
    },
    filter: function(b, c) {
        var a = new Hash;
        Hash.each(this,
        function(e, d) {
            if (b.call(c, e, d, this)) {
                a.set(d, e)
            }
        },
        this);
        return a
    },
    every: function(b, c) {
        for (var a in this) {
            if (this.hasOwnProperty(a) && !b.call(c, this[a], a)) {
                return false
            }
        }
        return true
    },
    some: function(b, c) {
        for (var a in this) {
            if (this.hasOwnProperty(a) && b.call(c, this[a], a)) {
                return true
            }
        }
        return false
    },
    getKeys: function() {
        var a = [];
        Hash.each(this,
        function(c, b) {
            a.push(b)
        });
        return a
    },
    getValues: function() {
        var a = [];
        Hash.each(this,
        function(b) {
            a.push(b)
        });
        return a
    },
    toQueryString: function(a) {
        var b = [];
        Hash.each(this,
        function(f, e) {
            if (a) {
                e = a + "[" + e + "]"
            }
            var d;
            switch ($type(f)) {
            case "object":
                d = Hash.toQueryString(f, e);
                break;
            case "array":
                var c = {};
                f.each(function(h, g) {
                    c[g] = h
                });
                d = Hash.toQueryString(c, e);
                break;
            default:
                d = e + "=" + encodeURIComponent(f)
            }
            if (f != undefined) {
                b.push(d)
            }
        });
        return b.join("&")
    }
});
Hash.alias({
    keyOf: "indexOf",
    hasValue: "contains"
});
var Event = new Native({
    name: "Event",
    initialize: function(a, f) {
        f = f || window;
        var k = f.document;
        a = a || f.event;
        if (a.$extended) {
            return a
        }
        this.$extended = true;
        var j = a.type;
        var g = a.target || a.srcElement;
        while (g && g.nodeType == 3) {
            g = g.parentNode
        }
        if (j.test(/key/)) {
            var b = a.which || a.keyCode;
            var m = Event.Keys.keyOf(b);
            if (j == "keydown") {
                var d = b - 111;
                if (d > 0 && d < 13) {
                    m = "f" + d
                }
            }
            m = m || String.fromCharCode(b).toLowerCase()
        } else {
            if (j.match(/(click|mouse|menu)/i)) {
                k = (!k.compatMode || k.compatMode == "CSS1Compat") ? k.html: k.body;
                var i = {
                    x: a.pageX || a.clientX + k.scrollLeft,
                    y: a.pageY || a.clientY + k.scrollTop
                };
                var c = {
                    x: (a.pageX) ? a.pageX - f.pageXOffset: a.clientX,
                    y: (a.pageY) ? a.pageY - f.pageYOffset: a.clientY
                };
                if (j.match(/DOMMouseScroll|mousewheel/)) {
                    var h = (a.wheelDelta) ? a.wheelDelta / 120 : -(a.detail || 0) / 3
                }
                var e = (a.which == 3) || (a.button == 2);
                var l = null;
                if (j.match(/over|out/)) {
                    switch (j) {
                    case "mouseover":
                        l = a.relatedTarget || a.fromElement;
                        break;
                    case "mouseout":
                        l = a.relatedTarget || a.toElement
                    }
                    if (! (function() {
                        while (l && l.nodeType == 3) {
                            l = l.parentNode
                        }
                        return true
                    }).create({
                        attempt: Browser.Engine.gecko
                    })()) {
                        l = false
                    }
                }
            }
        }
        return $extend(this, {
            event: a,
            type: j,
            page: i,
            client: c,
            rightClick: e,
            wheel: h,
            relatedTarget: l,
            target: g,
            code: b,
            key: m,
            shift: a.shiftKey,
            control: a.ctrlKey,
            alt: a.altKey,
            meta: a.metaKey
        })
    }
});
Event.Keys = new Hash({
    enter: 13,
    up: 38,
    down: 40,
    left: 37,
    right: 39,
    esc: 27,
    space: 32,
    backspace: 8,
    tab: 9,
    "delete": 46
});
Event.implement({
    stop: function() {
        return this.stopPropagation().preventDefault()
    },
    stopPropagation: function() {
        if (this.event.stopPropagation) {
            this.event.stopPropagation()
        } else {
            this.event.cancelBubble = true
        }
        return this
    },
    preventDefault: function() {
        if (this.event.preventDefault) {
            this.event.preventDefault()
        } else {
            this.event.returnValue = false
        }
        return this
    }
});
function Class(b) {
    if (b instanceof Function) {
        b = {
            initialize: b
        }
    }
    var a = function() {
        Object.reset(this);
        if (a._prototyping) {
            return this
        }
        this._current = $empty;
        var c = (this.initialize) ? this.initialize.apply(this, arguments) : this;
        delete this._current;
        delete this.caller;
        return c
    }.extend(this);
    a.implement(b);
    a.constructor = Class;
    a.prototype.constructor = a;
    return a
}
Function.prototype.protect = function() {
    this._protected = true;
    return this
};
Object.reset = function(a, c) {
    if (c == null) {
        for (var e in a) {
            Object.reset(a, e)
        }
        return a
    }
    delete a[c];
    switch ($type(a[c])) {
    case "object":
        var d = function() {};
        d.prototype = a[c];
        var b = new d;
        a[c] = Object.reset(b);
        break;
    case "array":
        a[c] = $unlink(a[c]);
        break
    }
    return a
};
new Native({
    name: "Class",
    initialize: Class
}).extend({
    instantiate: function(b) {
        b._prototyping = true;
        var a = new b;
        delete b._prototyping;
        return a
    },
    wrap: function(a, b, c) {
        if (c._origin) {
            c = c._origin
        }
        return function() {
            if (c._protected && this._current == null) {
                throw new Error('The method "' + b + '" cannot be called.')
            }
            var e = this.caller,
            f = this._current;
            this.caller = f;
            this._current = arguments.callee;
            var d = c.apply(this, arguments);
            this._current = f;
            this.caller = e;
            return d
        }.extend({
            _owner: a,
            _origin: c,
            _name: b
        })
    }
});
Class.implement({
    implement: function(a, d) {
        if ($type(a) == "object") {
            for (var e in a) {
                this.implement(e, a[e])
            }
            return this
        }
        var f = Class.Mutators[a];
        if (f) {
            d = f.call(this, d);
            if (d == null) {
                return this
            }
        }
        var c = this.prototype;
        switch ($type(d)) {
        case "function":
            if (d._hidden) {
                return this
            }
            c[a] = Class.wrap(this, a, d);
            break;
        case "object":
            var b = c[a];
            if ($type(b) == "object") {
                $mixin(b, d)
            } else {
                c[a] = $unlink(d)
            }
            break;
        case "array":
            c[a] = $unlink(d);
            break;
        default:
            c[a] = d
        }
        return this
    }
});
Class.Mutators = {
    Extends: function(a) {
        this.parent = a;
        this.prototype = Class.instantiate(a);
        this.implement("parent",
        function() {
            var b = this.caller._name,
            c = this.caller._owner.parent.prototype[b];
            if (!c) {
                throw new Error('The method "' + b + '" has no parent.')
            }
            return c.apply(this, arguments)
        }.protect())
    },
    Implements: function(a) {
        $splat(a).each(function(b) {
            if (b instanceof Function) {
                b = Class.instantiate(b)
            }
            this.implement(b)
        },
        this)
    }
};
var Chain = new Class({
    $chain: [],
    chain: function() {
        this.$chain.extend(Array.flatten(arguments));
        return this
    },
    callChain: function() {
        return (this.$chain.length) ? this.$chain.shift().apply(this, arguments) : false
    },
    clearChain: function() {
        this.$chain.empty();
        return this
    }
});
var Events = new Class({
    $events: {},
    addEvent: function(c, b, a) {
        c = Events.removeOn(c);
        if (b != $empty) {
            this.$events[c] = this.$events[c] || [];
            this.$events[c].include(b);
            if (a) {
                b.internal = true
            }
        }
        return this
    },
    addEvents: function(a) {
        for (var b in a) {
            this.addEvent(b, a[b])
        }
        return this
    },
    fireEvent: function(c, b, a) {
        c = Events.removeOn(c);
        if (!this.$events || !this.$events[c]) {
            return this
        }
        this.$events[c].each(function(d) {
            d.create({
                bind: this,
                delay: a,
                "arguments": b
            })()
        },
        this);
        return this
    },
    removeEvent: function(b, a) {
        b = Events.removeOn(b);
        if (!this.$events[b]) {
            return this
        }
        if (!a.internal) {
            this.$events[b].erase(a)
        }
        return this
    },
    removeEvents: function(c) {
        var d;
        if ($type(c) == "object") {
            for (d in c) {
                this.removeEvent(d, c[d])
            }
            return this
        }
        if (c) {
            c = Events.removeOn(c)
        }
        for (d in this.$events) {
            if (c && c != d) {
                continue
            }
            var b = this.$events[d];
            for (var a = b.length; a--; a) {
                this.removeEvent(d, b[a])
            }
        }
        return this
    }
});
Events.removeOn = function(a) {
    return a.replace(/^on([A-Z])/,
    function(b, c) {
        return c.toLowerCase()
    })
};
var Options = new Class({
    setOptions: function() {
        this.options = $merge.run([this.options].extend(arguments));
        if (!this.addEvent) {
            return this
        }
        for (var a in this.options) {
            if ($type(this.options[a]) != "function" || !(/^on[A-Z]/).test(a)) {
                continue
            }
            this.addEvent(a, this.options[a]);
            delete this.options[a]
        }
        return this
    }
});
var Element = new Native({
    name: "Element",
    legacy: window.Element,
    initialize: function(a, b) {
        var c = Element.Constructors.get(a);
        if (c) {
            return c(b)
        }
        if (typeof a == "string") {
            return document.newElement(a, b)
        }
        return document.id(a).set(b)
    },
    afterImplement: function(a, b) {
        Element.Prototype[a] = b;
        if (Array[a]) {
            return
        }
        Elements.implement(a,
        function() {
            var c = [],
            g = true;
            for (var e = 0,
            d = this.length; e < d; e++) {
                var f = this[e][a].apply(this[e], arguments);
                c.push(f);
                if (g) {
                    g = ($type(f) == "element")
                }
            }
            return (g) ? new Elements(c) : c
        })
    }
});
Element.Prototype = {
    $family: {
        name: "element"
    }
};
Element.Constructors = new Hash;
var IFrame = new Native({
    name: "IFrame",
    generics: false,
    initialize: function() {
        var f = Array.link(arguments, {
            properties: Object.type,
            iframe: $defined
        });
        var d = f.properties || {};
        var c = document.id(f.iframe);
        var e = d.onload || $empty;
        delete d.onload;
        d.id = d.name = $pick(d.id, d.name, c ? (c.id || c.name) : "IFrame_" + $time());
        c = new Element(c || "iframe", d);
        var b = function() {
            var g = $try(function() {
                return c.contentWindow.location.host
            });
            if (!g || g == window.location.host) {
                var h = new Window(c.contentWindow);
                new Document(c.contentWindow.document);
                $extend(h.Element.prototype, Element.Prototype)
            }
            e.call(c.contentWindow, c.contentWindow.document)
        };
        var a = $try(function() {
            return c.contentWindow
        }); ((a && a.document.body) || window.frames[d.id]) ? b() : c.addListener("load", b);
        return c
    }
});
var Elements = new Native({
    initialize: function(f, b) {
        b = $extend({
            ddup: true,
            cash: true
        },
        b);
        f = f || [];
        if (b.ddup || b.cash) {
            var g = {},
            e = [];
            for (var c = 0,
            a = f.length; c < a; c++) {
                var d = document.id(f[c], !b.cash);
                if (b.ddup) {
                    if (g[d.uid]) {
                        continue
                    }
                    g[d.uid] = true
                }
                if (d) {
                    e.push(d)
                }
            }
            f = e
        }
        return (b.cash) ? $extend(f, this) : f
    }
});
Elements.implement({
    filter: function(a, b) {
        if (!a) {
            return this
        }
        return new Elements(Array.filter(this, (typeof a == "string") ?
        function(c) {
            return c.match(a)
        }: a, b))
    }
});
Document.implement({
    newElement: function(a, b) {
        if (Browser.Engine.trident && b) { ["name", "type", "checked"].each(function(c) {
                if (!b[c]) {
                    return
                }
                a += " " + c + '="' + b[c] + '"';
                if (c != "checked") {
                    delete b[c]
                }
            });
            a = "<" + a + ">"
        }
        return document.id(this.createElement(a)).set(b)
    },
    newTextNode: function(a) {
        return this.createTextNode(a)
    },
    getDocument: function() {
        return this
    },
    getWindow: function() {
        return this.window
    },
    id: (function() {
        var a = {
            string: function(d, c, b) {
                d = b.getElementById(d);
                return (d) ? a.element(d, c) : null
            },
            element: function(b, e) {
                $uid(b);
                if (!e && !b.$family && !(/^object|embed$/i).test(b.tagName)) {
                    var c = Element.Prototype;
                    for (var d in c) {
                        b[d] = c[d]
                    }
                }
                return b
            },
            object: function(c, d, b) {
                if (c.toElement) {
                    return a.element(c.toElement(b), d)
                }
                return null
            }
        };
        a.textnode = a.whitespace = a.window = a.document = $arguments(0);
        return function(c, e, d) {
            if (c && c.$family && c.uid) {
                return c
            }
            var b = $type(c);
            return (a[b]) ? a[b](c, e, d || document) : null
        }
    })()
});
if (window.$ == null) {
    Window.implement({
        $: function(a, b) {
            return document.id(a, b, this.document)
        }
    })
}
Window.implement({
    $$: function(a) {
        if (arguments.length == 1 && typeof a == "string") {
            return this.document.getElements(a)
        }
        var f = [];
        var c = Array.flatten(arguments);
        for (var d = 0,
        b = c.length; d < b; d++) {
            var e = c[d];
            switch ($type(e)) {
            case "element":
                f.push(e);
                break;
            case "string":
                f.extend(this.document.getElements(e, true))
            }
        }
        return new Elements(f)
    },
    getDocument: function() {
        return this.document
    },
    getWindow: function() {
        return this
    }
});
Native.implement([Element, Document], {
    getElement: function(a, b) {
        return document.id(this.getElements(a, true)[0] || null, b)
    },
    getElements: function(a, d) {
        a = a.split(",");
        var c = [];
        var b = (a.length > 1);
        a.each(function(e) {
            var f = this.getElementsByTagName(e.trim()); (b) ? c.extend(f) : c = f
        },
        this);
        return new Elements(c, {
            ddup: b,
            cash: !d
        })
    }
}); (function() {
    var h = {},
    f = {};
    var i = {
        input: "checked",
        option: "selected",
        textarea: (Browser.Engine.webkit && Browser.Engine.version < 420) ? "innerHTML": "value"
    };
    var c = function(l) {
        return (f[l] || (f[l] = {}))
    };
    var g = function(n, l) {
        if (!n) {
            return
        }
        var m = n.uid;
        if (Browser.Engine.trident) {
            if (n.clearAttributes) {
                var q = l && n.cloneNode(false);
                n.clearAttributes();
                if (q) {
                    n.mergeAttributes(q)
                }
            } else {
                if (n.removeEvents) {
                    n.removeEvents()
                }
            }
            if ((/object/i).test(n.tagName)) {
                for (var o in n) {
                    if (typeof n[o] == "function") {
                        n[o] = $empty
                    }
                }
                Element.dispose(n)
            }
        }
        if (!m) {
            return
        }
        h[m] = f[m] = null
    };
    var d = function() {
        Hash.each(h, g);
        if (Browser.Engine.trident) {
            $A(document.getElementsByTagName("object")).each(g)
        }
        if (window.CollectGarbage) {
            CollectGarbage()
        }
        h = f = null
    };
    var j = function(n, l, s, m, p, r) {
        var o = n[s || l];
        var q = [];
        while (o) {
            if (o.nodeType == 1 && (!m || Element.match(o, m))) {
                if (!p) {
                    return document.id(o, r)
                }
                q.push(o)
            }
            o = o[l]
        }
        return (p) ? new Elements(q, {
            ddup: false,
            cash: !r
        }) : null
    };
    var e = {
        html: "innerHTML",
        "class": "className",
        "for": "htmlFor",
        defaultValue: "defaultValue",
        text: (Browser.Engine.trident || (Browser.Engine.webkit && Browser.Engine.version < 420)) ? "innerText": "textContent"
    };
    var b = ["compact", "nowrap", "ismap", "declare", "noshade", "checked", "disabled", "readonly", "multiple", "selected", "noresize", "defer"];
    var k = ["value", "type", "defaultValue", "accessKey", "cellPadding", "cellSpacing", "colSpan", "frameBorder", "maxLength", "readOnly", "rowSpan", "tabIndex", "useMap"];
    b = b.associate(b);
    Hash.extend(e, b);
    Hash.extend(e, k.associate(k.map(String.toLowerCase)));
    var a = {
        before: function(m, l) {
            if (l.parentNode) {
                l.parentNode.insertBefore(m, l)
            }
        },
        after: function(m, l) {
            if (!l.parentNode) {
                return
            }
            var n = l.nextSibling; (n) ? l.parentNode.insertBefore(m, n) : l.parentNode.appendChild(m)
        },
        bottom: function(m, l) {
            l.appendChild(m)
        },
        top: function(m, l) {
            var n = l.firstChild; (n) ? l.insertBefore(m, n) : l.appendChild(m)
        }
    };
    a.inside = a.bottom;
    Hash.each(a,
    function(l, m) {
        m = m.capitalize();
        Element.implement("inject" + m,
        function(n) {
            l(this, document.id(n, true));
            return this
        });
        Element.implement("grab" + m,
        function(n) {
            l(document.id(n, true), this);
            return this
        })
    });
    Element.implement({
        set: function(o, m) {
            switch ($type(o)) {
            case "object":
                for (var n in o) {
                    this.set(n, o[n])
                }
                break;
            case "string":
                var l = Element.Properties.get(o); (l && l.set) ? l.set.apply(this, Array.slice(arguments, 1)) : this.setProperty(o, m)
            }
            return this
        },
        get: function(m) {
            var l = Element.Properties.get(m);
            return (l && l.get) ? l.get.apply(this, Array.slice(arguments, 1)) : this.getProperty(m)
        },
        erase: function(m) {
            var l = Element.Properties.get(m); (l && l.erase) ? l.erase.apply(this) : this.removeProperty(m);
            return this
        },
        setProperty: function(m, n) {
            var l = e[m];
            if (n == undefined) {
                return this.removeProperty(m)
            }
            if (l && b[m]) {
                n = !!n
            } (l) ? this[l] = n: this.setAttribute(m, "" + n);
            return this
        },
        setProperties: function(l) {
            for (var m in l) {
                this.setProperty(m, l[m])
            }
            return this
        },
        getProperty: function(m) {
            var l = e[m];
            var n = (l) ? this[l] : this.getAttribute(m, 2);
            return (b[m]) ? !!n: (l) ? n: n || null
        },
        getProperties: function() {
            var l = $A(arguments);
            return l.map(this.getProperty, this).associate(l)
        },
        removeProperty: function(m) {
            var l = e[m]; (l) ? this[l] = (l && b[m]) ? false: "": this.removeAttribute(m);
            return this
        },
        removeProperties: function() {
            Array.each(arguments, this.removeProperty, this);
            return this
        },
        hasClass: function(l) {
            return this.className.contains(l, " ")
        },
        addClass: function(l) {
            if (!this.hasClass(l)) {
                this.className = (this.className + " " + l).clean()
            }
            return this
        },
        removeClass: function(l) {
            this.className = this.className.replace(new RegExp("(^|\\s)" + l + "(?:\\s|$)"), "$1");
            return this
        },
        toggleClass: function(l) {
            return this.hasClass(l) ? this.removeClass(l) : this.addClass(l)
        },
        adopt: function() {
            Array.flatten(arguments).each(function(l) {
                l = document.id(l, true);
                if (l) {
                    this.appendChild(l)
                }
            },
            this);
            return this
        },
        appendText: function(m, l) {
            return this.grab(this.getDocument().newTextNode(m), l)
        },
        grab: function(m, l) {
            a[l || "bottom"](document.id(m, true), this);
            return this
        },
        inject: function(m, l) {
            a[l || "bottom"](this, document.id(m, true));
            return this
        },
        replaces: function(l) {
            l = document.id(l, true);
            l.parentNode.replaceChild(this, l);
            return this
        },
        wraps: function(m, l) {
            m = document.id(m, true);
            return this.replaces(m).grab(m, l)
        },
        getPrevious: function(l, m) {
            return j(this, "previousSibling", null, l, false, m)
        },
        getAllPrevious: function(l, m) {
            return j(this, "previousSibling", null, l, true, m)
        },
        getNext: function(l, m) {
            return j(this, "nextSibling", null, l, false, m)
        },
        getAllNext: function(l, m) {
            return j(this, "nextSibling", null, l, true, m)
        },
        getFirst: function(l, m) {
            return j(this, "nextSibling", "firstChild", l, false, m)
        },
        getLast: function(l, m) {
            return j(this, "previousSibling", "lastChild", l, false, m)
        },
        getParent: function(l, m) {
            return j(this, "parentNode", null, l, false, m)
        },
        getParents: function(l, m) {
            return j(this, "parentNode", null, l, true, m)
        },
        getSiblings: function(l, m) {
            return this.getParent().getChildren(l, m).erase(this)
        },
        getChildren: function(l, m) {
            return j(this, "nextSibling", "firstChild", l, true, m)
        },
        getWindow: function() {
            return this.ownerDocument.window
        },
        getDocument: function() {
            return this.ownerDocument
        },
        getElementById: function(o, n) {
            var m = this.ownerDocument.getElementById(o);
            if (!m) {
                return null
            }
            for (var l = m.parentNode; l != this; l = l.parentNode) {
                if (!l) {
                    return null
                }
            }
            return document.id(m, n)
        },
        getSelected: function() {
            return new Elements($A(this.options).filter(function(l) {
                return l.selected
            }))
        },
        getComputedStyle: function(m) {
            if (this.currentStyle) {
                return this.currentStyle[m.camelCase()]
            }
            var l = this.getDocument().defaultView.getComputedStyle(this, null);
            return (l) ? l.getPropertyValue([m.hyphenate()]) : null
        },
        toQueryString: function() {
            var l = [];
            this.getElements("input, select, textarea", true).each(function(m) {
                if (!m.name || m.disabled || m.type == "submit" || m.type == "reset" || m.type == "file") {
                    return
                }
                var n = (m.tagName.toLowerCase() == "select") ? Element.getSelected(m).map(function(o) {
                    return o.value
                }) : ((m.type == "radio" || m.type == "checkbox") && !m.checked) ? null: m.value;
                $splat(n).each(function(o) {
                    if (typeof o != "undefined") {
                        l.push(m.name + "=" + encodeURIComponent(o))
                    }
                })
            });
            return l.join("&")
        },
        clone: function(o, l) {
            o = o !== false;
            var r = this.cloneNode(o);
            var n = function(v, u) {
                if (!l) {
                    v.removeAttribute("id")
                }
                if (Browser.Engine.trident) {
                    v.clearAttributes();
                    v.mergeAttributes(u);
                    v.removeAttribute("uid");
                    if (v.options) {
                        var w = v.options,
                        s = u.options;
                        for (var t = w.length; t--;) {
                            w[t].selected = s[t].selected
                        }
                    }
                }
                var x = i[u.tagName.toLowerCase()];
                if (x && u[x]) {
                    v[x] = u[x]
                }
            };
            if (o) {
                var p = r.getElementsByTagName("*"),
                q = this.getElementsByTagName("*");
                for (var m = p.length; m--;) {
                    n(p[m], q[m])
                }
            }
            n(r, this);
            return document.id(r)
        },
        destroy: function() {
            Element.empty(this);
            Element.dispose(this);
            g(this, true);
            return null
        },
        empty: function() {
            $A(this.childNodes).each(function(l) {
                Element.destroy(l)
            });
            return this
        },
        dispose: function() {
            return (this.parentNode) ? this.parentNode.removeChild(this) : this
        },
        hasChild: function(l) {
            l = document.id(l, true);
            if (!l) {
                return false
            }
            if (Browser.Engine.webkit && Browser.Engine.version < 420) {
                return $A(this.getElementsByTagName(l.tagName)).contains(l)
            }
            return (this.contains) ? (this != l && this.contains(l)) : !!(this.compareDocumentPosition(l) & 16)
        },
        match: function(l) {
            return (!l || (l == this) || (Element.get(this, "tag") == l))
        }
    });
    Native.implement([Element, Window, Document], {
        addListener: function(o, n) {
            if (o == "unload") {
                var l = n,
                m = this;
                n = function() {
                    m.removeListener("unload", n);
                    l()
                }
            } else {
                h[this.uid] = this
            }
            if (this.addEventListener) {
                this.addEventListener(o, n, false)
            } else {
                this.attachEvent("on" + o, n)
            }
            return this
        },
        removeListener: function(m, l) {
            if (this.removeEventListener) {
                this.removeEventListener(m, l, false)
            } else {
                this.detachEvent("on" + m, l)
            }
            return this
        },
        retrieve: function(m, l) {
            var o = c(this.uid),
            n = o[m];
            if (l != undefined && n == undefined) {
                n = o[m] = l
            }
            return $pick(n)
        },
        store: function(m, l) {
            var n = c(this.uid);
            n[m] = l;
            return this
        },
        eliminate: function(l) {
            var m = c(this.uid);
            delete m[l];
            return this
        }
    });
    window.addListener("unload", d)
})();
Element.Properties = new Hash;
Element.Properties.style = {
    set: function(a) {
        this.style.cssText = a
    },
    get: function() {
        return this.style.cssText
    },
    erase: function() {
        this.style.cssText = ""
    }
};
Element.Properties.tag = {
    get: function() {
        return this.tagName.toLowerCase()
    }
};
Element.Properties.html = (function() {
    var c = document.createElement("div");
    var a = {
        table: [1, "<table>", "</table>"],
        select: [1, "<select>", "</select>"],
        tbody: [2, "<table><tbody>", "</tbody></table>"],
        tr: [3, "<table><tbody><tr>", "</tr></tbody></table>"]
    };
    a.thead = a.tfoot = a.tbody;
    var b = {
        set: function() {
            var e = Array.flatten(arguments).join("");
            var f = Browser.Engine.trident && a[this.get("tag")];
            if (f) {
                var g = c;
                g.innerHTML = f[1] + e + f[2];
                for (var d = f[0]; d--;) {
                    g = g.firstChild
                }
                this.empty().adopt(g.childNodes)
            } else {
                this.innerHTML = e
            }
        }
    };
    b.erase = b.set;
    return b
})();
if (Browser.Engine.webkit && Browser.Engine.version < 420) {
    Element.Properties.text = {
        get: function() {
            if (this.innerText) {
                return this.innerText
            }
            var a = this.ownerDocument.newElement("div", {
                html: this.innerHTML
            }).inject(this.ownerDocument.body);
            var b = a.innerText;
            a.destroy();
            return b
        }
    }
}
Element.Properties.events = {
    set: function(a) {
        this.addEvents(a)
    }
};
Native.implement([Element, Window, Document], {
    addEvent: function(e, g) {
        var h = this.retrieve("events", {});
        h[e] = h[e] || {
            keys: [],
            values: []
        };
        if (h[e].keys.contains(g)) {
            return this
        }
        h[e].keys.push(g);
        var f = e,
        a = Element.Events.get(e),
        c = g,
        i = this;
        if (a) {
            if (a.onAdd) {
                a.onAdd.call(this, g)
            }
            if (a.condition) {
                c = function(j) {
                    if (a.condition.call(this, j)) {
                        return g.call(this, j)
                    }
                    return true
                }
            }
            f = a.base || f
        }
        var d = function() {
            return g.call(i)
        };
        var b = Element.NativeEvents[f];
        if (b) {
            if (b == 2) {
                d = function(j) {
                    j = new Event(j, i.getWindow());
                    if (c.call(i, j) === false) {
                        j.stop()
                    }
                }
            }
            this.addListener(f, d)
        }
        h[e].values.push(d);
        return this
    },
    removeEvent: function(c, b) {
        var a = this.retrieve("events");
        if (!a || !a[c]) {
            return this
        }
        var f = a[c].keys.indexOf(b);
        if (f == -1) {
            return this
        }
        a[c].keys.splice(f, 1);
        var e = a[c].values.splice(f, 1)[0];
        var d = Element.Events.get(c);
        if (d) {
            if (d.onRemove) {
                d.onRemove.call(this, b)
            }
            c = d.base || c
        }
        return (Element.NativeEvents[c]) ? this.removeListener(c, e) : this
    },
    addEvents: function(a) {
        for (var b in a) {
            this.addEvent(b, a[b])
        }
        return this
    },
    removeEvents: function(a) {
        var c;
        if ($type(a) == "object") {
            for (c in a) {
                this.removeEvent(c, a[c])
            }
            return this
        }
        var b = this.retrieve("events");
        if (!b) {
            return this
        }
        if (!a) {
            for (c in b) {
                this.removeEvents(c)
            }
            this.eliminate("events")
        } else {
            if (b[a]) {
                while (b[a].keys[0]) {
                    this.removeEvent(a, b[a].keys[0])
                }
                b[a] = null
            }
        }
        return this
    },
    fireEvent: function(d, b, a) {
        var c = this.retrieve("events");
        if (!c || !c[d]) {
            return this
        }
        c[d].keys.each(function(e) {
            e.create({
                bind: this,
                delay: a,
                "arguments": b
            })()
        },
        this);
        return this
    },
    cloneEvents: function(d, a) {
        d = document.id(d);
        var c = d.retrieve("events");
        if (!c) {
            return this
        }
        if (!a) {
            for (var b in c) {
                this.cloneEvents(d, b)
            }
        } else {
            if (c[a]) {
                c[a].keys.each(function(e) {
                    this.addEvent(a, e)
                },
                this)
            }
        }
        return this
    }
});
Element.NativeEvents = {
    click: 2,
    dblclick: 2,
    mouseup: 2,
    mousedown: 2,
    contextmenu: 2,
    mousewheel: 2,
    DOMMouseScroll: 2,
    mouseover: 2,
    mouseout: 2,
    mousemove: 2,
    selectstart: 2,
    selectend: 2,
    keydown: 2,
    keypress: 2,
    keyup: 2,
    focus: 2,
    blur: 2,
    change: 2,
    reset: 2,
    select: 2,
    submit: 2,
    load: 1,
    unload: 1,
    beforeunload: 2,
    resize: 1,
    move: 1,
    DOMContentLoaded: 1,
    readystatechange: 1,
    error: 1,
    abort: 1,
    scroll: 1
}; (function() {
    var a = function(b) {
        var c = b.relatedTarget;
        if (c == undefined) {
            return true
        }
        if (c === false) {
            return false
        }
        return ($type(this) != "document" && c != this && c.prefix != "xul" && !this.hasChild(c))
    };
    Element.Events = new Hash({
        mouseenter: {
            base: "mouseover",
            condition: a
        },
        mouseleave: {
            base: "mouseout",
            condition: a
        },
        mousewheel: {
            base: (Browser.Engine.gecko) ? "DOMMouseScroll": "mousewheel"
        }
    })
})();
Element.Properties.styles = {
    set: function(a) {
        this.setStyles(a)
    }
};
Element.Properties.opacity = {
    set: function(a, b) {
        if (!b) {
            if (a == 0) {
                if (this.style.visibility != "hidden") {
                    this.style.visibility = "hidden"
                }
            } else {
                if (this.style.visibility != "visible") {
                    this.style.visibility = "visible"
                }
            }
        }
        if (!this.currentStyle || !this.currentStyle.hasLayout) {
            this.style.zoom = 1
        }
        if (Browser.Engine.trident) {
            this.style.filter = (a == 1) ? "": "alpha(opacity=" + a * 100 + ")"
        }
        this.style.opacity = a;
        this.store("opacity", a)
    },
    get: function() {
        return this.retrieve("opacity", 1)
    }
};
Element.implement({
    setOpacity: function(a) {
        return this.set("opacity", a, true)
    },
    getOpacity: function() {
        return this.get("opacity")
    },
    setStyle: function(b, a) {
        switch (b) {
        case "opacity":
            return this.set("opacity", parseFloat(a));
        case "float":
            b = (Browser.Engine.trident) ? "styleFloat": "cssFloat"
        }
        b = b.camelCase();
        if ($type(a) != "string") {
            var c = (Element.Styles.get(b) || "@").split(" ");
            a = $splat(a).map(function(e, d) {
                if (!c[d]) {
                    return ""
                }
                return ($type(e) == "number") ? c[d].replace("@", Math.round(e)) : e
            }).join(" ")
        } else {
            if (a == String(Number(a))) {
                a = Math.round(a)
            }
        }
        this.style[b] = a;
        return this
    },
    getStyle: function(g) {
        switch (g) {
        case "opacity":
            return this.get("opacity");
        case "float":
            g = (Browser.Engine.trident) ? "styleFloat": "cssFloat"
        }
        g = g.camelCase();
        var a = this.style[g];
        if (!$chk(a)) {
            a = [];
            for (var f in Element.ShortStyles) {
                if (g != f) {
                    continue
                }
                for (var e in Element.ShortStyles[f]) {
                    a.push(this.getStyle(e))
                }
                return a.join(" ")
            }
            a = this.getComputedStyle(g)
        }
        if (a) {
            a = String(a);
            var c = a.match(/rgba?\([\d\s,]+\)/);
            if (c) {
                a = a.replace(c[0], c[0].rgbToHex())
            }
        }
        if (Browser.Engine.presto || (Browser.Engine.trident && !$chk(parseInt(a, 10)))) {
            if (g.test(/^(height|width)$/)) {
                var b = (g == "width") ? ["left", "right"] : ["top", "bottom"],
                d = 0;
                b.each(function(h) {
                    d += this.getStyle("border-" + h + "-width").toInt() + this.getStyle("padding-" + h).toInt()
                },
                this);
                return this["offset" + g.capitalize()] - d + "px"
            }
            if ((Browser.Engine.presto) && String(a).test("px")) {
                return a
            }
            if (g.test(/(border(.+)Width|margin|padding)/)) {
                return "0px"
            }
        }
        return a
    },
    setStyles: function(b) {
        for (var a in b) {
            this.setStyle(a, b[a])
        }
        return this
    },
    getStyles: function() {
        var a = {};
        Array.flatten(arguments).each(function(b) {
            a[b] = this.getStyle(b)
        },
        this);
        return a
    }
});
Element.Styles = new Hash({
    left: "@px",
    top: "@px",
    bottom: "@px",
    right: "@px",
    width: "@px",
    height: "@px",
    maxWidth: "@px",
    maxHeight: "@px",
    minWidth: "@px",
    minHeight: "@px",
    backgroundColor: "rgb(@, @, @)",
    backgroundPosition: "@px @px",
    color: "rgb(@, @, @)",
    fontSize: "@px",
    letterSpacing: "@px",
    lineHeight: "@px",
    clip: "rect(@px @px @px @px)",
    margin: "@px @px @px @px",
    padding: "@px @px @px @px",
    border: "@px @ rgb(@, @, @) @px @ rgb(@, @, @) @px @ rgb(@, @, @)",
    borderWidth: "@px @px @px @px",
    borderStyle: "@ @ @ @",
    borderColor: "rgb(@, @, @) rgb(@, @, @) rgb(@, @, @) rgb(@, @, @)",
    zIndex: "@",
    zoom: "@",
    fontWeight: "@",
    textIndent: "@px",
    opacity: "@"
});
Element.ShortStyles = {
    margin: {},
    padding: {},
    border: {},
    borderWidth: {},
    borderStyle: {},
    borderColor: {}
}; ["Top", "Right", "Bottom", "Left"].each(function(g) {
    var f = Element.ShortStyles;
    var b = Element.Styles; ["margin", "padding"].each(function(h) {
        var i = h + g;
        f[h][i] = b[i] = "@px"
    });
    var e = "border" + g;
    f.border[e] = b[e] = "@px @ rgb(@, @, @)";
    var d = e + "Width",
    a = e + "Style",
    c = e + "Color";
    f[e] = {};
    f.borderWidth[d] = f[e][d] = b[d] = "@px";
    f.borderStyle[a] = f[e][a] = b[a] = "@";
    f.borderColor[c] = f[e][c] = b[c] = "rgb(@, @, @)"
}); (function() {
    Element.implement({
        scrollTo: function(h, i) {
            if (b(this)) {
                this.getWindow().scrollTo(h, i)
            } else {
                this.scrollLeft = h;
                this.scrollTop = i
            }
            return this
        },
        getSize: function() {
            if (b(this)) {
                return this.getWindow().getSize()
            }
            return {
                x: this.offsetWidth,
                y: this.offsetHeight
            }
        },
        getScrollSize: function() {
            if (b(this)) {
                return this.getWindow().getScrollSize()
            }
            return {
                x: this.scrollWidth,
                y: this.scrollHeight
            }
        },
        getScroll: function() {
            if (b(this)) {
                return this.getWindow().getScroll()
            }
            return {
                x: this.scrollLeft,
                y: this.scrollTop
            }
        },
        getScrolls: function() {
            var i = this,
            h = {
                x: 0,
                y: 0
            };
            while (i && !b(i)) {
                h.x += i.scrollLeft;
                h.y += i.scrollTop;
                i = i.parentNode
            }
            return h
        },
        getOffsetParent: function() {
            var h = this;
            if (b(h)) {
                return null
            }
            if (!Browser.Engine.trident) {
                return h.offsetParent
            }
            while ((h = h.parentNode) && !b(h)) {
                if (d(h, "position") != "static") {
                    return h
                }
            }
            return null
        },
        getOffsets: function() {
            if (this.getBoundingClientRect) {
                var j = this.getBoundingClientRect(),
                m = document.id(this.getDocument().documentElement),
                p = m.getScroll(),
                k = this.getScrolls(),
                i = this.getScroll(),
                h = (d(this, "position") == "fixed");
                return {
                    x: j.left.toInt() + k.x - i.x + ((h) ? 0 : p.x) - m.clientLeft,
                    y: j.top.toInt() + k.y - i.y + ((h) ? 0 : p.y) - m.clientTop
                }
            }
            var l = this,
            n = {
                x: 0,
                y: 0
            };
            if (b(this)) {
                return n
            }
            while (l && !b(l)) {
                n.x += l.offsetLeft;
                n.y += l.offsetTop;
                if (Browser.Engine.gecko) {
                    if (!f(l)) {
                        n.x += c(l);
                        n.y += g(l)
                    }
                    var o = l.parentNode;
                    if (o && d(o, "overflow") != "visible") {
                        n.x += c(o);
                        n.y += g(o)
                    }
                } else {
                    if (l != this && Browser.Engine.webkit) {
                        n.x += c(l);
                        n.y += g(l)
                    }
                }
                l = l.offsetParent
            }
            if (Browser.Engine.gecko && !f(this)) {
                n.x -= c(this);
                n.y -= g(this)
            }
            return n
        },
        getPosition: function(k) {
            if (b(this)) {
                return {
                    x: 0,
                    y: 0
                }
            }
            var l = this.getOffsets(),
            i = this.getScrolls();
            var h = {
                x: l.x - i.x,
                y: l.y - i.y
            };
            var j = (k && (k = document.id(k))) ? k.getPosition() : {
                x: 0,
                y: 0
            };
            return {
                x: h.x - j.x,
                y: h.y - j.y
            }
        },
        getCoordinates: function(j) {
            if (b(this)) {
                return this.getWindow().getCoordinates()
            }
            var h = this.getPosition(j),
            i = this.getSize();
            var k = {
                left: h.x,
                top: h.y,
                width: i.x,
                height: i.y
            };
            k.right = k.left + k.width;
            k.bottom = k.top + k.height;
            return k
        },
        computePosition: function(h) {
            return {
                left: h.x - e(this, "margin-left"),
                top: h.y - e(this, "margin-top")
            }
        },
        setPosition: function(h) {
            return this.setStyles(this.computePosition(h))
        }
    });
    Native.implement([Document, Window], {
        getSize: function() {
            if (Browser.Engine.presto || Browser.Engine.webkit) {
                var i = this.getWindow();
                return {
                    x: i.innerWidth,
                    y: i.innerHeight
                }
            }
            var h = a(this);
            return {
                x: h.clientWidth,
                y: h.clientHeight
            }
        },
        getScroll: function() {
            var i = this.getWindow(),
            h = a(this);
            return {
                x: i.pageXOffset || h.scrollLeft,
                y: i.pageYOffset || h.scrollTop
            }
        },
        getScrollSize: function() {
            var i = a(this),
            h = this.getSize();
            return {
                x: Math.max(i.scrollWidth, h.x),
                y: Math.max(i.scrollHeight, h.y)
            }
        },
        getPosition: function() {
            return {
                x: 0,
                y: 0
            }
        },
        getCoordinates: function() {
            var h = this.getSize();
            return {
                top: 0,
                left: 0,
                bottom: h.y,
                right: h.x,
                height: h.y,
                width: h.x
            }
        }
    });
    var d = Element.getComputedStyle;
    function e(h, i) {
        return d(h, i).toInt() || 0
    }
    function f(h) {
        return d(h, "-moz-box-sizing") == "border-box"
    }
    function g(h) {
        return e(h, "border-top-width")
    }
    function c(h) {
        return e(h, "border-left-width")
    }
    function b(h) {
        return (/^(?:body|html)$/i).test(h.tagName)
    }
    function a(h) {
        var i = h.getDocument();
        return (!i.compatMode || i.compatMode == "CSS1Compat") ? i.html: i.body
    }
})();
Element.alias("setPosition", "position");
Native.implement([Window, Document, Element], {
    getHeight: function() {
        return this.getSize().y
    },
    getWidth: function() {
        return this.getSize().x
    },
    getScrollTop: function() {
        return this.getScroll().y
    },
    getScrollLeft: function() {
        return this.getScroll().x
    },
    getScrollHeight: function() {
        return this.getScrollSize().y
    },
    getScrollWidth: function() {
        return this.getScrollSize().x
    },
    getTop: function() {
        return this.getPosition().y
    },
    getLeft: function() {
        return this.getPosition().x
    }
});
Native.implement([Document, Element], {
    getElements: function(h, g) {
        h = h.split(",");
        var c, e = {};
        for (var d = 0,
        b = h.length; d < b; d++) {
            var a = h[d],
            f = Selectors.Utils.search(this, a, e);
            if (d != 0 && f.item) {
                f = $A(f)
            }
            c = (d == 0) ? f: (c.item) ? $A(c).concat(f) : c.concat(f)
        }
        return new Elements(c, {
            ddup: (h.length > 1),
            cash: !g
        })
    }
});
Element.implement({
    match: function(b) {
        if (!b || (b == this)) {
            return true
        }
        var d = Selectors.Utils.parseTagAndID(b);
        var a = d[0],
        e = d[1];
        if (!Selectors.Filters.byID(this, e) || !Selectors.Filters.byTag(this, a)) {
            return false
        }
        var c = Selectors.Utils.parseSelector(b);
        return (c) ? Selectors.Utils.filter(this, c, {}) : true
    }
});
var Selectors = {
    Cache: {
        nth: {},
        parsed: {}
    }
};
Selectors.RegExps = {
    id: (/#([\w-]+)/),
    tag: (/^(\w+|\*)/),
    quick: (/^(\w+|\*)$/),
    splitter: (/\s*([+>~\s])\s*([a-zA-Z#.*:\[])/g),
    combined: (/\.([\w-]+)|\[(\w+)(?:([!*^$~|]?=)(["']?)([^\4]*?)\4)?\]|:([\w-]+)(?:\(["']?(.*?)?["']?\)|$)/g)
};
Selectors.Utils = {
    chk: function(b, c) {
        if (!c) {
            return true
        }
        var a = $uid(b);
        if (!c[a]) {
            return c[a] = true
        }
        return false
    },
    parseNthArgument: function(h) {
        if (Selectors.Cache.nth[h]) {
            return Selectors.Cache.nth[h]
        }
        var e = h.match(/^([+-]?\d*)?([a-z]+)?([+-]?\d*)?$/);
        if (!e) {
            return false
        }
        var g = parseInt(e[1], 10);
        var d = (g || g === 0) ? g: 1;
        var f = e[2] || false;
        var c = parseInt(e[3], 10) || 0;
        if (d != 0) {
            c--;
            while (c < 1) {
                c += d
            }
            while (c >= d) {
                c -= d
            }
        } else {
            d = c;
            f = "index"
        }
        switch (f) {
        case "n":
            e = {
                a: d,
                b: c,
                special: "n"
            };
            break;
        case "odd":
            e = {
                a: 2,
                b: 0,
                special: "n"
            };
            break;
        case "even":
            e = {
                a: 2,
                b: 1,
                special: "n"
            };
            break;
        case "first":
            e = {
                a: 0,
                special: "index"
            };
            break;
        case "last":
            e = {
                special: "last-child"
            };
            break;
        case "only":
            e = {
                special: "only-child"
            };
            break;
        default:
            e = {
                a: (d - 1),
                special: "index"
            }
        }
        return Selectors.Cache.nth[h] = e
    },
    parseSelector: function(e) {
        if (Selectors.Cache.parsed[e]) {
            return Selectors.Cache.parsed[e]
        }
        var d, h = {
            classes: [],
            pseudos: [],
            attributes: []
        };
        while ((d = Selectors.RegExps.combined.exec(e))) {
            var i = d[1],
            g = d[2],
            f = d[3],
            b = d[5],
            c = d[6],
            j = d[7];
            if (i) {
                h.classes.push(i)
            } else {
                if (c) {
                    var a = Selectors.Pseudo.get(c);
                    if (a) {
                        h.pseudos.push({
                            parser: a,
                            argument: j
                        })
                    } else {
                        h.attributes.push({
                            name: c,
                            operator: "=",
                            value: j
                        })
                    }
                } else {
                    if (g) {
                        h.attributes.push({
                            name: g,
                            operator: f,
                            value: b
                        })
                    }
                }
            }
        }
        if (!h.classes.length) {
            delete h.classes
        }
        if (!h.attributes.length) {
            delete h.attributes
        }
        if (!h.pseudos.length) {
            delete h.pseudos
        }
        if (!h.classes && !h.attributes && !h.pseudos) {
            h = null
        }
        return Selectors.Cache.parsed[e] = h
    },
    parseTagAndID: function(b) {
        var a = b.match(Selectors.RegExps.tag);
        var c = b.match(Selectors.RegExps.id);
        return [(a) ? a[1] : "*", (c) ? c[1] : false]
    },
    filter: function(f, c, e) {
        var d;
        if (c.classes) {
            for (d = c.classes.length; d--; d) {
                var g = c.classes[d];
                if (!Selectors.Filters.byClass(f, g)) {
                    return false
                }
            }
        }
        if (c.attributes) {
            for (d = c.attributes.length; d--; d) {
                var b = c.attributes[d];
                if (!Selectors.Filters.byAttribute(f, b.name, b.operator, b.value)) {
                    return false
                }
            }
        }
        if (c.pseudos) {
            for (d = c.pseudos.length; d--; d) {
                var a = c.pseudos[d];
                if (!Selectors.Filters.byPseudo(f, a.parser, a.argument, e)) {
                    return false
                }
            }
        }
        return true
    },
    getByTagAndID: function(b, a, d) {
        if (d) {
            var c = (b.getElementById) ? b.getElementById(d, true) : Element.getElementById(b, d, true);
            return (c && Selectors.Filters.byTag(c, a)) ? [c] : []
        } else {
            return b.getElementsByTagName(a)
        }
    },
    search: function(o, h, t) {
        var b = [];
        var c = h.trim().replace(Selectors.RegExps.splitter,
        function(k, j, i) {
            b.push(j);
            return ":)" + i
        }).split(":)");
        var p, e, A;
        for (var z = 0,
        v = c.length; z < v; z++) {
            var y = c[z];
            if (z == 0 && Selectors.RegExps.quick.test(y)) {
                p = o.getElementsByTagName(y);
                continue
            }
            var a = b[z - 1];
            var q = Selectors.Utils.parseTagAndID(y);
            var B = q[0],
            r = q[1];
            if (z == 0) {
                p = Selectors.Utils.getByTagAndID(o, B, r)
            } else {
                var d = {},
                g = [];
                for (var x = 0,
                w = p.length; x < w; x++) {
                    g = Selectors.Getters[a](g, p[x], B, r, d)
                }
                p = g
            }
            var f = Selectors.Utils.parseSelector(y);
            if (f) {
                e = [];
                for (var u = 0,
                s = p.length; u < s; u++) {
                    A = p[u];
                    if (Selectors.Utils.filter(A, f, t)) {
                        e.push(A)
                    }
                }
                p = e
            }
        }
        return p
    }
};
Selectors.Getters = {
    " ": function(h, g, j, a, e) {
        var d = Selectors.Utils.getByTagAndID(g, j, a);
        for (var c = 0,
        b = d.length; c < b; c++) {
            var f = d[c];
            if (Selectors.Utils.chk(f, e)) {
                h.push(f)
            }
        }
        return h
    },
    ">": function(h, g, j, a, f) {
        var c = Selectors.Utils.getByTagAndID(g, j, a);
        for (var e = 0,
        d = c.length; e < d; e++) {
            var b = c[e];
            if (b.parentNode == g && Selectors.Utils.chk(b, f)) {
                h.push(b)
            }
        }
        return h
    },
    "+": function(c, b, a, e, d) {
        while ((b = b.nextSibling)) {
            if (b.nodeType == 1) {
                if (Selectors.Utils.chk(b, d) && Selectors.Filters.byTag(b, a) && Selectors.Filters.byID(b, e)) {
                    c.push(b)
                }
                break
            }
        }
        return c
    },
    "~": function(c, b, a, e, d) {
        while ((b = b.nextSibling)) {
            if (b.nodeType == 1) {
                if (!Selectors.Utils.chk(b, d)) {
                    break
                }
                if (Selectors.Filters.byTag(b, a) && Selectors.Filters.byID(b, e)) {
                    c.push(b)
                }
            }
        }
        return c
    }
};
Selectors.Filters = {
    byTag: function(b, a) {
        return (a == "*" || (b.tagName && b.tagName.toLowerCase() == a))
    },
    byID: function(a, b) {
        return (!b || (a.id && a.id == b))
    },
    byClass: function(b, a) {
        return (b.className && b.className.contains && b.className.contains(a, " "))
    },
    byPseudo: function(a, d, c, b) {
        return d.call(a, c, b)
    },
    byAttribute: function(c, d, b, e) {
        var a = Element.prototype.getProperty.call(c, d);
        if (!a) {
            return (b == "!=")
        }
        if (!b || e == undefined) {
            return true
        }
        switch (b) {
        case "=":
            return (a == e);
        case "*=":
            return (a.contains(e));
        case "^=":
            return (a.substr(0, e.length) == e);
        case "$=":
            return (a.substr(a.length - e.length) == e);
        case "!=":
            return (a != e);
        case "~=":
            return a.contains(e, " ");
        case "|=":
            return a.contains(e, "-")
        }
        return false
    }
};
Selectors.Pseudo = new Hash({
    checked: function() {
        return this.checked
    },
    empty: function() {
        return ! (this.innerText || this.textContent || "").length
    },
    not: function(a) {
        return ! Element.match(this, a)
    },
    contains: function(a) {
        return (this.innerText || this.textContent || "").contains(a)
    },
    "first-child": function() {
        return Selectors.Pseudo.index.call(this, 0)
    },
    "last-child": function() {
        var a = this;
        while ((a = a.nextSibling)) {
            if (a.nodeType == 1) {
                return false
            }
        }
        return true
    },
    "only-child": function() {
        var b = this;
        while ((b = b.previousSibling)) {
            if (b.nodeType == 1) {
                return false
            }
        }
        var a = this;
        while ((a = a.nextSibling)) {
            if (a.nodeType == 1) {
                return false
            }
        }
        return true
    },
    "nth-child": function(g, e) {
        g = (g == undefined) ? "n": g;
        var c = Selectors.Utils.parseNthArgument(g);
        if (c.special != "n") {
            return Selectors.Pseudo[c.special].call(this, c.a, e)
        }
        var f = 0;
        e.positions = e.positions || {};
        var d = $uid(this);
        if (!e.positions[d]) {
            var b = this;
            while ((b = b.previousSibling)) {
                if (b.nodeType != 1) {
                    continue
                }
                f++;
                var a = e.positions[$uid(b)];
                if (a != undefined) {
                    f = a + f;
                    break
                }
            }
            e.positions[d] = f
        }
        return (e.positions[d] % c.a == c.b)
    },
    index: function(a) {
        var b = this,
        c = 0;
        while ((b = b.previousSibling)) {
            if (b.nodeType == 1 && ++c > a) {
                return false
            }
        }
        return (c == a)
    },
    even: function(b, a) {
        return Selectors.Pseudo["nth-child"].call(this, "2n+1", a)
    },
    odd: function(b, a) {
        return Selectors.Pseudo["nth-child"].call(this, "2n", a)
    },
    selected: function() {
        return this.selected
    },
    enabled: function() {
        return (this.disabled === false)
    }
});
Element.Events.domready = {
    onAdd: function(a) {
        if (Browser.loaded) {
            a.call(this)
        }
    }
}; (function() {
    var b = function() {
        if (Browser.loaded) {
            return
        }
        Browser.loaded = true;
        window.fireEvent("domready");
        document.fireEvent("domready")
    };
    window.addEvent("load", b);
    if (Browser.Engine.trident) {
        var a = document.createElement("div"); (function() { ($try(function() {
                a.doScroll();
                return document.id(a).inject(document.body).set("html", "temp").dispose()
            })) ? b() : arguments.callee.delay(50)
        })()
    } else {
        if (Browser.Engine.webkit && Browser.Engine.version < 525) { (function() { (["loaded", "complete"].contains(document.readyState)) ? b() : arguments.callee.delay(50)
            })()
        } else {
            document.addEvent("DOMContentLoaded", b)
        }
    }
})();
var JSON = new Hash(this.JSON && {
    stringify: JSON.stringify,
    parse: JSON.parse
}).extend({
    $specialChars: {
        "\b": "\\b",
        "\t": "\\t",
        "\n": "\\n",
        "\f": "\\f",
        "\r": "\\r",
        '"': '\\"',
        "\\": "\\\\"
    },
    $replaceChars: function(a) {
        return JSON.$specialChars[a] || "\\u00" + Math.floor(a.charCodeAt() / 16).toString(16) + (a.charCodeAt() % 16).toString(16)
    },
    encode: function(b) {
        switch ($type(b)) {
        case "string":
            return '"' + b.replace(/[\x00-\x1f\\"]/g, JSON.$replaceChars) + '"';
        case "array":
            return "[" + String(b.map(JSON.encode).clean()) + "]";
        case "object":
        case "hash":
            var a = [];
            Hash.each(b,
            function(e, d) {
                var c = JSON.encode(e);
                if (c) {
                    a.push(JSON.encode(d) + ":" + c)
                }
            });
            return "{" + a + "}";
        case "number":
        case "boolean":
            return String(b);
        case false:
            return "null"
        }
        return null
    },
    decode: function(string, secure) {
        if ($type(string) != "string" || !string.length) {
            return null
        }
        if (secure && !(/^[,:{}\[\]0-9.\-+Eaeflnr-u \n\r\t]*$/).test(string.replace(/\\./g, "@").replace(/"[^"\\\n\r]*"/g, ""))) {
            return null
        }
        return eval("(" + string + ")")
    }
});
Native.implement([Hash, Array, String, Number], {
    toJSON: function() {
        return JSON.encode(this)
    }
});
var Cookie = new Class({
    Implements: Options,
    options: {
        path: false,
        domain: false,
        duration: false,
        secure: false,
        document: document
    },
    initialize: function(b, a) {
        this.key = b;
        this.setOptions(a)
    },
    write: function(b) {
        b = encodeURIComponent(b);
        if (this.options.domain) {
            b += "; domain=" + this.options.domain
        }
        if (this.options.path) {
            b += "; path=" + this.options.path
        }
        if (this.options.duration) {
            var a = new Date();
            a.setTime(a.getTime() + this.options.duration * 24 * 60 * 60 * 1000);
            b += "; expires=" + a.toGMTString()
        }
        if (this.options.secure) {
            b += "; secure"
        }
        this.options.document.cookie = this.key + "=" + b;
        return this
    },
    read: function() {
        var a = this.options.document.cookie.match("(?:^|;)\\s*" + this.key.escapeRegExp() + "=([^;]*)");
        return (a) ? decodeURIComponent(a[1]) : null
    },
    dispose: function() {
        new Cookie(this.key, $merge(this.options, {
            duration: -1
        })).write("");
        return this
    }
});
Cookie.write = function(b, c, a) {
    return new Cookie(b, a).write(c)
};
Cookie.read = function(a) {
    return new Cookie(a).read()
};
Cookie.dispose = function(b, a) {
    return new Cookie(b, a).dispose()
};
var Request = new Class({
    Implements: [Chain, Events, Options],
    options: {
        url: "",
        data: "",
        headers: {
            "X-Requested-With": "XMLHttpRequest",
            Accept: "text/javascript, text/html, application/xml, text/xml, */*"
        },
        async: true,
        format: false,
        method: "post",
        link: "ignore",
        isSuccess: null,
        emulation: true,
        urlEncoded: true,
        encoding: "utf-8",
        evalScripts: false,
        evalResponse: false,
        noCache: false
    },
    initialize: function(a) {
        this.xhr = new Browser.Request();
        this.setOptions(a);
        this.options.isSuccess = this.options.isSuccess || this.isSuccess;
        this.headers = new Hash(this.options.headers)
    },
    onStateChange: function() {
        if (this.xhr.readyState != 4 || !this.running) {
            return
        }
        this.running = false;
        this.status = 0;
        $try(function() {
            this.status = this.xhr.status
        }.bind(this));
        this.xhr.onreadystatechange = $empty;
        if (this.options.isSuccess.call(this, this.status)) {
            this.response = {
                text: this.xhr.responseText,
                xml: this.xhr.responseXML
            };
            this.success(this.response.text, this.response.xml)
        } else {
            this.response = {
                text: null,
                xml: null
            };
            this.failure()
        }
    },
    isSuccess: function() {
        return ((this.status >= 200) && (this.status < 300))
    },
    processScripts: function(a) {
        if (this.options.evalResponse || (/(ecma|java)script/).test(this.getHeader("Content-type"))) {
            return $exec(a)
        }
        return a.stripScripts(this.options.evalScripts)
    },
    success: function(b, a) {
        this.onSuccess(this.processScripts(b), a)
    },
    onSuccess: function() {
        this.fireEvent("complete", arguments).fireEvent("success", arguments).callChain()
    },
    failure: function() {
        this.onFailure()
    },
    onFailure: function() {
        this.fireEvent("complete").fireEvent("failure", this.xhr)
    },
    setHeader: function(a, b) {
        this.headers.set(a, b);
        return this
    },
    getHeader: function(a) {
        return $try(function() {
            return this.xhr.getResponseHeader(a)
        }.bind(this))
    },
    check: function() {
        if (!this.running) {
            return true
        }
        switch (this.options.link) {
        case "cancel":
            this.cancel();
            return true;
        case "chain":
            this.chain(this.caller.bind(this, arguments));
            return false
        }
        return false
    },
    send: function(k) {
        if (!this.check(k)) {
            return this
        }
        this.running = true;
        var i = $type(k);
        if (i == "string" || i == "element") {
            k = {
                data: k
            }
        }
        var d = this.options;
        k = $extend({
            data: d.data,
            url: d.url,
            method: d.method
        },
        k);
        var g = k.data,
        b = String(k.url),
        a = k.method.toLowerCase();
        switch ($type(g)) {
        case "element":
            g = document.id(g).toQueryString();
            break;
        case "object":
        case "hash":
            g = Hash.toQueryString(g)
        }
        if (this.options.format) {
            var j = "format=" + this.options.format;
            g = (g) ? j + "&" + g: j
        }
        if (this.options.emulation && !["get", "post"].contains(a)) {
            var h = "_method=" + a;
            g = (g) ? h + "&" + g: h;
            a = "post"
        }
        if (this.options.urlEncoded && a == "post") {
            var c = (this.options.encoding) ? "; charset=" + this.options.encoding: "";
            this.headers.set("Content-type", "application/x-www-form-urlencoded" + c)
        }
        if (this.options.noCache) {
            var f = "noCache=" + new Date().getTime();
            g = (g) ? f + "&" + g: f
        }
        var e = b.lastIndexOf("/");
        if (e > -1 && (e = b.indexOf("#")) > -1) {
            b = b.substr(0, e)
        }
        if (g && a == "get") {
            b = b + (b.contains("?") ? "&": "?") + g;
            g = null
        }
        this.xhr.open(a.toUpperCase(), b, this.options.async);
        this.xhr.onreadystatechange = this.onStateChange.bind(this);
        this.headers.each(function(m, l) {
            try {
                this.xhr.setRequestHeader(l, m)
            } catch(n) {
                this.fireEvent("exception", [l, m])
            }
        },
        this);
        this.fireEvent("request");
        this.xhr.send(g);
        if (!this.options.async) {
            this.onStateChange()
        }
        return this
    },
    cancel: function() {
        if (!this.running) {
            return this
        }
        this.running = false;
        this.xhr.abort();
        this.xhr.onreadystatechange = $empty;
        this.xhr = new Browser.Request();
        this.fireEvent("cancel");
        return this
    }
}); (function() {
    var a = {}; ["get", "post", "put", "delete", "GET", "POST", "PUT", "DELETE"].each(function(b) {
        a[b] = function() {
            var c = Array.link(arguments, {
                url: String.type,
                data: $defined
            });
            return this.send($extend(c, {
                method: b
            }))
        }
    });
    Request.implement(a)
})();
Element.Properties.send = {
    set: function(a) {
        var b = this.retrieve("send");
        if (b) {
            b.cancel()
        }
        return this.eliminate("send").store("send:options", $extend({
            data: this,
            link: "cancel",
            method: this.get("method") || "post",
            url: this.get("action")
        },
        a))
    },
    get: function(a) {
        if (a || !this.retrieve("send")) {
            if (a || !this.retrieve("send:options")) {
                this.set("send", a)
            }
            this.store("send", new Request(this.retrieve("send:options")))
        }
        return this.retrieve("send")
    }
};
Element.implement({
    send: function(a) {
        var b = this.get("send");
        b.send({
            data: this,
            url: a || b.options.url
        });
        return this
    }
});
Request.HTML = new Class({
    Extends: Request,
    options: {
        update: false,
        append: false,
        evalScripts: true,
        filter: false
    },
    processHTML: function(c) {
        var b = c.match(/<body[^>]*>([\s\S]*?)<\/body>/i);
        c = (b) ? b[1] : c;
        var a = new Element("div");
        return $try(function() {
            var d = "<root>" + c + "</root>",
            g;
            if (Browser.Engine.trident) {
                g = new ActiveXObject("Microsoft.XMLDOM");
                g.async = false;
                g.loadXML(d)
            } else {
                g = new DOMParser().parseFromString(d, "text/xml")
            }
            d = g.getElementsByTagName("root")[0];
            if (!d) {
                return null
            }
            for (var f = 0,
            e = d.childNodes.length; f < e; f++) {
                var h = Element.clone(d.childNodes[f], true, true);
                if (h) {
                    a.grab(h)
                }
            }
            return a
        }) || a.set("html", c)
    },
    success: function(d) {
        var c = this.options,
        b = this.response;
        b.html = d.stripScripts(function(e) {
            b.javascript = e
        });
        var a = this.processHTML(b.html);
        b.tree = a.childNodes;
        b.elements = a.getElements("*");
        if (c.filter) {
            b.tree = b.elements.filter(c.filter)
        }
        if (c.update) {
            document.id(c.update).empty().set("html", b.html)
        } else {
            if (c.append) {
                document.id(c.append).adopt(a.getChildren())
            }
        }
        if (c.evalScripts) {
            $exec(b.javascript)
        }
        this.onSuccess(b.tree, b.elements, b.html, b.javascript)
    }
});
Element.Properties.load = {
    set: function(a) {
        var b = this.retrieve("load");
        if (b) {
            b.cancel()
        }
        return this.eliminate("load").store("load:options", $extend({
            data: this,
            link: "cancel",
            update: this,
            method: "get"
        },
        a))
    },
    get: function(a) {
        if (a || !this.retrieve("load")) {
            if (a || !this.retrieve("load:options")) {
                this.set("load", a)
            }
            this.store("load", new Request.HTML(this.retrieve("load:options")))
        }
        return this.retrieve("load")
    }
};
Element.implement({
    load: function() {
        this.get("load").send(Array.link(arguments, {
            data: Object.type,
            url: String.type
        }));
        return this
    }
});
Request.JSON = new Class({
    Extends: Request,
    options: {
        secure: true
    },
    initialize: function(a) {
        this.parent(a);
        this.headers.extend({
            Accept: "application/json",
            "X-Request": "JSON"
        })
    },
    success: function(a) {
        this.response.json = JSON.decode(a, this.options.secure);
        this.onSuccess(this.response.json, a)
    }
});
MooTools.More = {
    version: "1.2.4.2",
    build: "bd5a93c0913cce25917c48cbdacde568e15e02ef"
};
Class.Occlude = new Class({
    occlude: function(c, b) {
        b = document.id(b || this.element);
        var a = b.retrieve(c || this.property);
        if (a && !$defined(this.occluded)) {
            return this.occluded = a
        }
        this.occluded = false;
        b.store(c || this.property, this);
        return this.occluded
    }
});
var Asset = {
    javascript: function(f, d) {
        d = $extend({
            onload: $empty,
            document: document,
            check: $lambda(true)
        },
        d);
        var b = new Element("script", {
            src: f,
            type: "text/javascript"
        });
        var e = d.onload.bind(b),
        a = d.check,
        g = d.document;
        delete d.onload;
        delete d.check;
        delete d.document;
        b.addEvents({
            load: e,
            readystatechange: function() {
                if (["loaded", "complete"].contains(this.readyState)) {
                    e()
                }
            }
        }).set(d);
        if (Browser.Engine.webkit419) {
            var c = (function() {
                if (!$try(a)) {
                    return
                }
                $clear(c);
                e()
            }).periodical(50)
        }
        return b.inject(g.head)
    },
    css: function(b, a) {
        return new Element("link", $merge({
            rel: "stylesheet",
            media: "screen",
            type: "text/css",
            href: b
        },
        a)).inject(document.head)
    },
    image: function(c, b) {
        b = $merge({
            onload: $empty,
            onabort: $empty,
            onerror: $empty
        },
        b);
        var d = new Image();
        var a = document.id(d) || new Element("img"); ["load", "abort", "error"].each(function(e) {
            var f = "on" + e;
            var g = b[f];
            delete b[f];
            d[f] = function() {
                if (!d) {
                    return
                }
                if (!a.parentNode) {
                    a.width = d.width;
                    a.height = d.height
                }
                d = d.onload = d.onabort = d.onerror = null;
                g.delay(1, a, a);
                a.fireEvent(e, a, 1)
            }
        });
        d.src = a.src = c;
        if (d && d.complete) {
            d.onload.delay(1)
        }
        return a.set(b)
    },
    images: function(d, c) {
        c = $merge({
            onComplete: $empty,
            onProgress: $empty,
            onError: $empty,
            properties: {}
        },
        c);
        d = $splat(d);
        var a = [];
        var b = 0;
        return new Elements(d.map(function(e) {
            return Asset.image(e, $extend(c.properties, {
                onload: function() {
                    c.onProgress.call(this, b, d.indexOf(e));
                    b++;
                    if (b == d.length) {
                        c.onComplete()
                    }
                },
                onerror: function() {
                    c.onError.call(this, b, d.indexOf(e));
                    b++;
                    if (b == d.length) {
                        c.onComplete()
                    }
                }
            }))
        }))
    }
};
Hash.Cookie = new Class({
    Extends: Cookie,
    options: {
        autoSave: true
    },
    initialize: function(b, a) {
        this.parent(b, a);
        this.load()
    },
    save: function() {
        var a = JSON.encode(this.hash);
        if (!a || a.length > 4096) {
            return false
        }
        if (a == "{}") {
            this.dispose()
        } else {
            this.write(a)
        }
        return true
    },
    load: function() {
        this.hash = new Hash(JSON.decode(this.read(), true));
        return this
    }
});
Hash.each(Hash.prototype,
function(b, a) {
    if (typeof b == "function") {
        Hash.Cookie.implement(a,
        function() {
            var c = b.apply(this.hash, arguments);
            if (this.options.autoSave) {
                this.save()
            }
            return c
        })
    }
});
function include(c, a, b) {
    b = Array.slice(arguments, 2).flatten() || [];
    b.each(function(d) {
        document.write('<script type="text/javascript" charset="' + a + '" src="' + c + d + '"><\/script>')
    })
}
function require(e, a, c) {
    c = Array.slice(arguments, 2).flatten() || [];
    var b = document.getElements("script[src]").map(function(d) {
        return d.get("src")
    });
    c = c.filter(function(d, f) {
        return ! b.contains(e + d)
    });
    c.each(function(d) {
        Asset.javascript.delay(10, Asset.javascript, [e + d, {
            charset: a || "GBK"
        }])
    })
}; (function(a) {
    a.getTimeSpan = function(c, k, n) {
        c = parseInt(c);
        c = c == c ? c: 0;
        n = (n || 60 * 1000) / 1000;
        var e = 60,
        l = Math.abs($time() - c),
        i = k || (Ku6.empty(k) ? "": "�ո�");
        var o = Math.floor(l / (e * 1000)),
        f = Math.floor(o / 60),
        g = Math.floor(f / 24),
        b = Math.floor(g / 30),
        j = Math.floor(b / 12);
        if (j) {
            i = j + "��"
        } else {
            if (b) {
                i = b + "��"
            } else {
                if (g) {
                    i = g + "��"
                } else {
                    if (f > 0 && f * e * 60 >= n) {
                        i = f + "Сʱ"
                    } else {
                        if (o > 0 && o * e >= n) {
                            i = o + "����"
                        } else {
                            return i
                        }
                    }
                }
            }
        }
        if (l > (n || e) * 1000) {
            i += "ǰ"
        }
        return i
    };
    a.getFormatMVTime = function(f) {
        var e = 0,
        b = 0,
        d = 0,
        c = parseInt(f, 0);
        d = c % 60;
        b = Math.floor((c - d) / 60);
        e = Math.ceil((c - b * 60 - d) / 3600);
        return (e ? (e < 10 ? "0": "") + e + ":": "") + (b < 10 ? "0": "") + b + ":" + (d < 10 ? "0": "") + d
    };
    a.getMVTime = function(d, g, j, k, c, b) {
        var i = 0,
        e = 0,
        l = 0,
        f = parseInt(d, 0),
        j = j || 3;
        l = f % 60;
        e = Math.floor((f - l) / 60);
        i = Math.ceil((f - e * 60 - l) / 3600);
        if (j < 3 && (!i && !e)) {
            e = 1
        } else {
            if (j < 2 && !i) {
                i = 1
            }
        }
        return (i ? (g && i < 10 ? "0": "") + i + (k || (Ku6.empty(k) ? "": ":")) : "") + (j > 1 && e ? (g && e < 10 ? "0": "") + e + (c || (Ku6.empty(c) ? "": ":")) : "") + (j > 2 && l ? (g && l < 10 ? "0": "") + l + (b || "") : "")
    };
    a.getAge = function(b) {
        return (new Date()).getFullYear() - (new Date(b)).getFullYear()
    }
})(using("Ku6.Utils")); (function(b) {
    var a = {
        unknown: {
            text: "�ϴ���",
            codes: ",0,"
        },
        normal: {
            text: "��",
            codes: ",19,20,21,22,"
        },
        converting: {
            text: "ת����",
            codes: ",1,"
        },
        auditing: {
            text: "�����",
            codes: ",10,11,12,13,"
        },
        covertFaild: {
            text: "ת��ʧ��",
            codes: ",-10,"
        },
        removedBySelf: {
            text: "�Լ�ɾ��",
            codes: ",-30,-31,-32,-33,"
        },
        removedBySystem: {
            text: "�༭ɾ��",
            codes: ",-20,-40,"
        }
    };
    b.VideoStatus = {
        get: function(d) {
            d = parseInt(d);
            d = d == d ? d: 0;
            var c = {
                text: "δ֪"
            };
            Ku6.each(a,
            function(f, e) {
                c[e] = f.codes.indexOf("," + d + ",") >= 0;
                if (c[e]) {
                    c.text = f.text
                }
            });
            return c
        }
    }
})(using("Ku6.Utils"));
using("Ku6.Plugin").AutoCompleteTip = new Class({
    Implements: [Options, Events],
    options: {
        minWidth: 280,
        size: 10,
        fetchDelay: 500,
        autoFetch: true,
        fixResize: true,
        zIndex: 500,
        boxCls: "acBox",
        resultsCls: "acRs",
        resultCls: "acR",
        resultFocusCls: "focus",
        keyCls: "acRK",
        statCls: "acRC",
        closeCls: "acC",
        prefix: "_ac_",
        statSuffix: " videos",
        closeLang: " close"
    },
    initialize: function(b) {
        this.guid = Ku6.guid();
        this.bound = {};
        this.setOptions(b);
        var a = this.options;
        this.$name = a.name;
        this.resultPrefix = a.prefix + this.guid + "r";
        if (a.input) {
            this.inputEl = $(a.input)
        }
        this.alignEl = $(a.align, true) ? $(a.align) : this.inputEl;
        this.attach();
        return this
    },
    elMouseOver: function(a) {
        this.autoHideTimer = $clear(this.autoHideTimer);
        if (this.inputEl) {
            this.inputEl.focus()
        }
    },
    resultsMouseOver: function(c) {
        var b = $(c.target, true),
        a = b ? b.tagName.toLowerCase() : "";
        b = null;
        if (a == "li") {
            if (c) {
                c.stop()
            }
            this.changeFocus(c.target)
        }
    },
    resultsMouseDown: function(h) {
        if (h) {
            h.stop()
        }
        var f = this.options,
        b = $(h.target),
        a = b ? b.tagName.toLowerCase() : "";
        if (a == "ul") {
            return
        }
        if (a == "span" && b.hasClass(f.closeCls)) {
            this.hide();
            return
        }
        if (a != "li") {
            b = b.getParent()
        }
        if (b.hasClass(f.resultCls)) {
            var d = b.getElement("span." + f.keyCls).get("text");
            this.changeFocus(b).fillKey(d);
            var c = b.get("id").replace(this.resultPrefix, ""),
            g = b.getElement("span." + f.statCls).get("text").replace(f.statSuffix, "");
            this.fireEvent("click", [c, d, g])
        }
    },
    resultsMouseUp: function(a) {
        if (a) {
            a.stop()
        }
        this.hide()
    },
    inputKeyUp: function(c) {
        if (c.key == "up") {
            this.scroll(true)
        } else {
            if (c.key == "down") {
                this.scroll()
            } else {
                var a = this.inputEl.value;
                if (a.length == 0) {
                    this.lastSearch = "";
                    this.hide(true)
                } else {
                    if (a == this.lastSearch) {
                        this.show()
                    } else {
                        this.fetchDelayHD = $clear(this.fetchDelayHD);
                        var b = this.options;
                        if (b.fetchDelay > 0) {
                            this.fetchDelayHD = this.fetch.delay(b.fetchDelay, this, a)
                        } else {
                            this.fetch(a)
                        }
                    }
                }
            }
        }
    },
    inputBlur: function() {
        var a = this.options;
        if (a.autoHide) {
            if (a.hideDelay > 0) {
                this.autoHideTimer = this.hide.delay(a.hideDelay, this)
            } else {
                this.hide()
            }
        }
    },
    build: function() {
        if (!this.builded) {
            var a = this.options;
            this.el = new Element("div", {
                "class": a.boxCls,
                styles: {
                    display: "none",
                    "z-index": a.zIndex,
                    width: Math.max(this.inputEl.offsetWidth, a.minWidth)
                }
            }).set("html", (a.iframeFix ? Ku6.FRAME_FIX_HTML: "") + '<ul class="' + a.resultsCls + '"></ul>').addEvent("mouseover", this.bound.elOver).inject(document.body);
            this.resultsEl = this.el.getElement("ul");
            if (this.resultsEl) {
                this.resultsEl.addEvents({
                    mouseover: this.bound.rsMOver,
                    mousedown: this.bound.rsMDown,
                    mouseup: this.bound.rsMUp
                })
            }
            this.builded = true;
            if (a.autoFetch === true) {
                this.fetchDelayHD = this.fetch.delay(a.fetchDelay, this)
            }
        }
        return this
    },
    attach: function() {
        var a = this.bound;
        a.elOver = this.elMouseOver.bindWithEvent(this);
        a.rsMOver = this.resultsMouseOver.bindWithEvent(this);
        a.rsMDown = this.resultsMouseDown.bindWithEvent(this);
        a.rsMUp = this.resultsMouseUp.bindWithEvent(this);
        a.inpKUp = this.inputKeyUp.bindWithEvent(this);
        a.inpBlur = this.inputBlur.bindWithEvent(this);
        a.inpFocus = function(b) {
            if (this.inputEl.value.length == 0) {
                this.hide()
            } else {
                if (this.resultsEl && this.resultsEl.childNodes.length > 0) {
                    this.show()
                }
            }
        }.bindWithEvent(this);
        if (this.inputEl) {
            this.inputEl.set("autocomplete", "off").addEvent("keyup", a.inpKUp);
            this.inputEl.addEvent("focus", a.inpFocus);
            if (this.options.autoHide) {
                this.inputEl.addEvent("blur", a.inpBlur)
            }
        }
        if (this.options.fixResize) {
            a.resize = this.align.bindWithEvent(this);
            window.addEvent("resize", a.resize)
        }
        return this
    },
    align: function() {
        var b = this.options;
        if (this.el && this.alignEl) {
            var d = this.alignEl.getCoordinates(),
            a = d.left,
            e = d.top + d.height;
            if (b.styleFix) {
                var c = this.alignEl.getStyles("marginTop", "marginBottom", "paddingTop", "paddingBottom");
                e = e - c.marginTop.toInt() - c.marginBottom.toInt() - c.paddingTop.toInt() - c.paddingBottom.toInt()
            }
            this.moveTo({
                left: a - (b.rightAlign ? this.el.getSize().x - d.width: 0),
                top: e
            })
        }
        return this
    },
    show: function() {
        if (!this.builded) {
            this.build()
        } else {
            $clear(this.autoHideTimer);
            if (this.resultsEl && !this.resultsEl.get("text").trim()) {
                return this
            }
            if (!this.showing) {
                this.showing = true;
                this.align();
                this.el.show()
            }
            if (this.options.iframeFix) {
                var a = this.el.getSize(),
                b = this.el.getElement("iframe");
                if (b) {
                    b.setStyles({
                        height: a.y,
                        width: a.x
                    })
                }
                b = null
            }
        }
        return this
    },
    hide: function(a) {
        if (this.showing) {
            this.showing = false;
            if (this.el) {
                this.el.hide()
            }
        }
        if (a) {
            this.cleanResult()
        }
        return this
    },
    moveTo: function(a) {
        if (this.el && a) {
            this.el.setStyles(a)
        }
        return this
    },
    changeFocus: function(a) {
        a = $(a);
        var b = this.options;
        if (a && a.hasClass(b.resultCls)) {
            var c = this.resultPrefix;
            var d = $(c + this.focusIndex, true);
            if (d) {
                d.removeClass(b.resultFocusCls)
            }
            a.addClass(b.resultFocusCls);
            this.focusIndex = parseInt(a.get("id").replace(c, ""))
        }
        return this
    },
    scroll: function(d) {
        if (!this.builded) {
            this.build()
        }
        if (this.resultsEl) {
            var c = this.options,
            a = this.resultsEl.getElements("li");
            if (a.length > 0) {
                this.show();
                var e = this.focusIndex;
                if (e >= 0) {
                    a[e].removeClass(c.resultFocusCls)
                }
                if (d) {
                    var b = e >= 0 ? (e - 1) : (a.length - 2)
                } else {
                    var b = (e + 2) >= a.length ? -1 : (e + 1)
                }
                if (b >= 0) {
                    a[b].addClass(c.resultFocusCls)
                }
                this.focusIndex = b;
                this.fillKey(b >= 0 ? a[b].getElement("span." + c.keyCls).get("html") : this.lastSearch)
            }
            a = null
        }
        return this
    },
    fillKey: function(a) {
        var b = this.inputEl;
        if (b) {
            b.focus();
            b.value = a;
            b.select()
        }
        return this
    },
    fetch: function() {
        if (!this.inputEl) {
            return
        }
        var a = this.inputEl.value;
        if (a.length == 0 || a == this.lastSearch) {
            return
        }
        this.lastTime = $time();
        var b = this.options.action;
        if (b) {
            b += b.indexOf("?") >= 0 ? "&": "?";
            b += "key=" + encodeURIComponent(a) + "&n=" + this.$name + ".response&cp=" + this.lastTime;
            Ku6.Utils.importJs(b, {
                onLoad: function(c) {
                    Element.dispose(c)
                }
            });
            this.onFetch(a)
        }
    },
    onFetch: function(a) {
        this.lastSearch = a
    },
    response: function(a, b) {
        if (typeof a == "object" && this.lastTime == b && (this.inputEl.value != "")) {
            this.render(a)
        }
    },
    onEmpty: function() {
        this.hide().cleanResult()
    },
    cleanResult: function() {
        this.focusIndex = -1;
        if (this.resultsEl) {
            this.resultsEl.empty()
        }
    },
    filterHtml: function(c) {
        var b = this.options,
        a = [];
        a.push('<span class="' + b.statCls + '">' + c.c + b.statSuffix + "</span>");
        a.push('<span class="' + b.keyCls + '">' + c.k + "</span>");
        return a.join("")
    },
    render: function(c) {
        if (!this.builded) {
            this.build()
        }
        if (!c || c.length == 0) {
            this.onEmpty()
        } else {
            if (this.resultsEl) {
                var b = [];
                var d = this.options;
                for (var a = 0,
                e = c.length; a < e; a++) {
                    b.push('<li id="' + this.resultPrefix + a + '" class="' + d.resultCls + '">');
                    b.push(this.filterHtml(c[a]));
                    b.push("</li>")
                }
                b.push('<li style="padding:0 3px;"><span class="' + d.closeCls + '">' + d.closeLang + "</span><span>&nbsp;</span></li>");
                this.resultsEl.set("html", b.join(""));
                b = null;
                d = null;
                this.focusIndex = -1;
                this.show()
            }
        }
    }
}); (function(a) {
    a.VButton = new Class({
        Implements: [Events, Options],
        options: {
            autoExpand: true,
            index: 0,
            swfSize: {
                x: 375,
                y: 230
            },
            showTime: 1 * 60 * 1000,
            trollTime: 5 * 60 * 1000,
            expandTime: 30 * 1000,
            inductionDelay: 300
        },
        initialize: function(b) {
            this.setOptions(b);
            this.bound = {};
            var c = this.options;
            this.index = c.index;
            this.el = $(c.container);
            this.options.container = null;
            this.PARENT_CLASS = a.VButton;
            if (this.el) {
                if (Browser.loaded) {
                    this.build()
                } else {
                    window.addEvent("domready", this.build.bind(this))
                }
            }
        },
        build: function() {
            if (this.builded) {
                return
            }
            var b = this.options;
            if (b.items && b.items.length > 0) {
                this.attachEvent();
                this.items = $splat(b.items).sort($random.pass([ - 2, 2]));
                if (b.gc && Browser.Engine.trident) {
                    window.addEvent("unload",
                    function() {
                        window.removeEvent("unload", arguments.callee);
                        this.detachEvent()
                    }.bind(this))
                }
                this.troll(true)
            }
            this.builded = true
        },
        attachEvent: function() {
            this.bound.mOver = this.PARENT_CLASS.mouseHandle.bindWithEvent(this.PARENT_CLASS, [this, false]);
            this.bound.mOut = this.PARENT_CLASS.mouseHandle.bindWithEvent(this.PARENT_CLASS, [this, true]);
            if (this.el) {
                this.el.addEvents({
                    mouseover: this.bound.mOver,
                    mouseout: this.bound.mOut
                })
            }
        },
        detachEvent: function() {
            if (this.el) {
                this.el.removeEvents({
                    mouseover: this.bound.mOver,
                    mouseout: this.bound.mOut
                })
            }
            this.bound = {}
        },
        clearTimer: function() { ["idTimer", "tlTimer", "epTimer", "ephTimer"].each(function(b) {
                $clear(this[b])
            },
            this);
            return this
        },
        troll: function(b) {
            if (!b) {++this.index;
                if (this.index > (this.items.length - 1)) {
                    this.index = 0
                }
            }
            this.el.set("html", this.PARENT_CLASS.makeHtml(this, this.index));
            if (this.options.autoExpand) {
                this.PARENT_CLASS.startExpandLinster(this, b && (this.items.length < 2))
            } else {
                this.PARENT_CLASS.startTrollLinster(this)
            }
        },
        switchMode: function(e) {
            var b = this.el.getElementsByTagName("img")[0],
            d = this.el.getElementsByTagName("embed")[0],
            c = this.options.swfSize;
            if (d) {
                d.height = e ? 1 : c.y;
                d.width = e ? 1 : c.x
            }
            if (b) {
                b.style.display = e ? "": "none"
            }
            return this
        }
    });
    Ku6.extend(a.VButton, {
        TPL_KEY: "TPL_VBUTTON",
        makeHtml: function(b, c) {
            var d = b.items[c];
            return d ? Ku6.Utils.getTpl(b.PARENT_CLASS.TPL_KEY).substitute({
                link: d.stat,
                image: d.img,
                swf: d.swf
            }) : ""
        },
        mouseHandle: function(d, b, f) {
            if (d) {
                new Event(d).preventDefault()
            }
            if (f) {
                var c = b.options.inductionDelay;
                c > 0 ? (b.idTimer = b.switchMode.delay(c, b, [true])) : b.switchMode(true);
                b.PARENT_CLASS.startTrollLinster(b)
            } else {
                b.clearTimer().switchMode()
            }
        },
        startTrollLinster: function(b) {
            b.tlTimer = b.troll.delay(b.options.trollTime, b)
        },
        startExpandLinster: function(b, c) {
            b.epTimer = function() {
                b.switchMode();
                b.ephTimer = function() {
                    b.switchMode(true);
                    if (c !== true) {
                        b.PARENT_CLASS.startTrollLinster(b)
                    }
                }.delay(b.options.expandTime)
            }.delay(b.options.showTime)
        }
    })
})(using("Ku6.Plugin"));
using("Ku6.Plugin").FloatWindow = new Class({
    Implements: [Events, Options],
    options: {
        allwayShow: true,
        zIndex: 500
    },
    initialize: function(a) {
        this.bound = {};
        this.setOptions(a);
        this.isMin = this.options.min === true;
        this.posFixed = !!Ku6.Browser.POSITION_FIXED;
        if (this.options.auto) {
            this.start()
        }
    },
    start: function() {
        if (Browser.loaded) {
            this.build()
        } else {
            window.addEvent("domready", this.build.bind(this))
        }
    },
    build: function() {
        if (this.el) {
            return
        }
        var b = this.options;
        var a = $type(b.content);
        if (a == "element") {
            this.el = $unlink(b.content)
        } else {
            if (a == "string") {
                this.el = new Element("div").set("html", b.url ? '<iframe width="' + b.width + '" height="' + b.height + '" src="' + b.url + '" scrolling="no" frameborder="0" marginheight="0" marginwidth="0" hspace="0"></iframe>': (b.iframeFix ? Ku6.FRAME_FIX_HTML: "") + b.content);
                this.el.setStyles({
                    position: this.posFixed ? "absolute": "fixed",
                    bottom: b.bottom || 0,
                    right: b.right || 0,
                    zIndex: b.zIndex
                });
                if (b.width >= 0) {
                    this.el.setStyle("width", b.width)
                }
                if (b.height >= 0) {
                    this.el.setStyle("height", b.height)
                }
                this.el.inject(document.body)
            }
        }
        if (this.posFixed) {
            this.attach()
        }
        this.fireEvent("create")
    },
    destory: function() {
        if (this.posFixed) {
            window.removeEvent("scroll", this.bound.move)
        }
        this.bound = null
    },
    attach: function() {
        var a = this;
        this.bound.move = this.onScroll.bindWithEvent(this);
        window.addEvent("scroll", this.bound.move);
        window.addEvent("unload",
        function() {
            window.removeEvent("unload", arguments.callee);
            a.destory()
        })
    },
    onScroll: function(b) {
        if (this.el && !this.hided) {
            var a = this.el.offsetHeight;
            this.el.setStyle("top", window.getSize().y + window.getScroll().y - a)
        }
    },
    close: function() {
        if (!this.options.allwayShow) {
            this.hided = true;
            if (this.el) {
                this.el.hide()
            }
        }
        this.fireEvent("close")
    },
    open: function() {
        if (this.hided) {
            this.hided = false;
            if (this.el) {
                this.el.show()
            }
            this.fireEvent("open")
        }
    },
    min: function() {
        if (!this.hided && !this.isMin) {
            this.isMin = true;
            this.fireEvent("min");
            if (this.posFixed) {
                this.onScroll()
            }
        }
    },
    restore: function() {
        if (!this.hided && this.isMin) {
            this.isMin = false;
            this.fireEvent("restore");
            if (this.posFixed) {
                this.onScroll()
            }
        }
    },
    toggle: function() {
        this.isMin ? this.restore() : this.min()
    }
});
var QuickList = {
    $name: "QuickList",
    max: 30,
    sync: true,
    syncWait: 3000,
    hash: window.QL_HASH_KEY || "qlist",
    key: "QuickList",
    addCls: "plus",
    playCls: "arrow",
    counter: "qlCounter",
    init: function(a) {
        this.render(a);
        if (this.sync && !this.syncer) {
            this.syncer = this.doSync.periodical(this.syncWait, this)
        }
    },
    render: function(b) {
        this.retrieve();
        var a = {};
        this.value.each(function(c) {
            a[c] = true
        }); ($(b) || document).getElements("a." + this.addCls + ", a." + this.playCls).each(function(d) {
            if (d && d.hash.length > 1) {
                var c = a[d.hash.substr(1)];
                d.set("class", c ? this.playCls: this.addCls).set("title", c ? "���ŵ㲥��": "��ӵ��㲥��")
            }
        },
        this);
        this.count()
    },
    count: function() {
        var b = this.value.length;
        var a = $(this.counter);
        if (a) {
            a.set("text", (b > 0 ? "(" + b + ")": ""))
        }
    },
    doSync: function() {
        var a = this.value.join("");
        this.retrieve();
        if (a != this.value.join("")) {
            this.render();
            this.count();
            if ($type(this.onSync) == "function") {
                this.onSync(this.value)
            }
        }
    },
    toggle: function(b) {
        var b = new Event(b);
        b.preventDefault();
        var c = b.target;
        if (c.hash.length > 1) {
            var a = c.hash.substr(1);
            if (this.contains(a)) {
                this.play(a)
            } else {
                if (this.add(a, true)) {
                    c.className = this.playCls;
                    c.title = "���ŵ㲥��"
                }
            }
        }
    },
    clear: function() {
        this.value = [];
        this.store("", -1)
    },
    contains: function(a, b) {
        this.retrieve(b);
        return this.value.contains(a)
    },
    onAddMax: function() {
        alert("��Ǹ���㲥�����ʧ�ܣ�\t\t\n��ĵ�ǰ�㲥���е���Ƶ��Ŀ�Ѿ��ﵽ����(" + this.max + ")\n������Ƴ�ĳЩ��Ƶ������ӡ�")
    },
    add: function(a, b) {
        this.retrieve(b);
        if (this.value.length >= this.max) {
            this.onAddMax();
            return false
        }
        if (this.contains(a)) {
            return false
        }
        this.value.push(a);
        this.store(this.value.join(","));
        if ($type(this.onAdd) == "function") {
            this.onAdd(a)
        }
        return true
    },
    remove: function(a, b) {
        this.retrieve(b);
        if (this.contains(a)) {
            this.value.erase(a);
            this.store(this.value.join(","));
            if ($type(this.onRemove) == "function") {
                this.onRemove(a)
            }
        }
    },
    store: function(a, b) {
        Cookie.write(this.key, a, {
            domain: "ku6.com",
            path: "/",
            duration: (b || 366)
        });
        this.count()
    },
    retrieve: function(a) {
        if (!a || !this.value) {
            this.value = Cookie.read(this.key);
            this.value = (this.value ? this.value.split(",") : [])
        }
        return this.value
    },
    getPlayUrl: function(a) {
        return Ku6.Utils.getVideoUrl(a) + "#" + this.hash
    },
    play: function(b, c) {
        var a = $type(b) == "string" ? b: this.value[b]; (c || top).location.href = this.getPlayUrl(a)
    }
}; (function(F) {
    var b = Ku6,
    D = b.Urls,
    e = b.Utils,
    g = b.Cookie;
    var B = "logon",
    n = "sync",
    u = "validate",
    J = "render",
    x = "LOGON_TYPE_IN",
    v = "LOGON_TYPE_OUT",
    k = "logon",
    L = "sync",
    t = "init",
    m = "update",
    I = "timeout",
    f = "error",
    C = "exception",
    s = "busy",
    z = "TIP_MSG",
    c = "account",
    H = "password",
    M = "expire",
    G = "SSO_ENABLE",
    p = "ASYNC_SSO",
    i = "REDIRECT_WAIT_SYNC",
    d = "EXPIRE_DEF",
    w = D.space + "/login.htm?jumpUrl={0}",
    h = "/sso/sso.html?action={0}&callback={1}&lc={2}&dm={3}&ids={4}",
    O = D.passport + "/crossdomain.jsp?domainid={0}&action={1}&redirect={2}",
    E = ["overTime", "referKey", "redirectUrl", "referKey", "domainId", "ssoHost", "lc", "dm", "sso", "domains"];
    b.extend(F, new Events);
    b.each(E,
    function(P) {
        F["set" + e.capitalize(P)] = function(Q) {
            F[P] = Q;
            return this
        };
        F["get" + e.capitalize(P)] = function() {
            return F[P]
        }
    });
    F.setDomains([2, 3, 4]).setOverTime(30 * 1000).setReferKey("www");
    F[d] = 2 * 7 * 24 * 60 * 60;
    F[i] = 15000;
    F[G] = true;
    F[p] = true;
    F[x] = "login";
    F[v] = "logout";
    F[z] = ["\u9a8c\u8bc1\u5904\u7406\u4e2d,\u8bf7\u7a0d\u7b49...", "\u7528\u6237\u540d\u4e0d\u80fd\u4e3a\u7a7a", "\u7528\u6237\u540d\u4e0d\u80fd\u542b\u6709\u4e2d\u6587\u5b57\u7b26(\u8001\u7528\u6237\u9664\u5916)", "\u7528\u6237\u540d\u957f\u5ea64-16\u4f4d(1\u4e2d\u6587\u53602\u4f4d)", "\u5bc6\u7801\u4e0d\u80fd\u4e3a\u7a7a", "\u5bc6\u7801\u4e0d\u80fd\u542b\u6709\u4e2d\u6587\u5b57\u7b26", "\u5bc6\u7801\u957f\u5ea64-16\u4f4d", "\u7528\u6237\u540d\u4e0d\u5b58\u5728", "\u5bc6\u7801\u9519\u8bef", "\u53d1\u751f\u672a\u77e5\u9519\u8bef,\u8bf7\u91cd\u8bd5!"];
    var a = {};
    a[F[x]] = D.passport + "/nonibw-session-create.htm";
    a[F[v]] = D.passport + "/nonibw-session-remove.htm";
    function j() {
        return F.form ? F.form.elements: null
    }
    function y(Q, R) {
        var P, S;
        if (Q == "") {
            P = c;
            S = 1
        } else {
            if (R == "") {
                P = H;
                S = 4
            } else {
                if (R.length < 4 || R.length > 16) {
                    P = H;
                    S = 6
                } else {
                    if ((/[^\x00-\xff]/).test(R)) {
                        P = H;
                        S = 5
                    }
                }
            }
        }
        if (P && S >= 0) {
            F.fireEvent(f, [u, x, {
                msg: F[z][S],
                name: P
            },
            F.getDomainId()]);
            return false
        }
        return true
    }
    var q = {
        ing: "Ing",
        timer: "Timer"
    };
    function l(P, S) {
        var Q = P + q.ing,
        R = P + q.timer;
        F[Q] = !!S;
        F[R] = !!!S ? $clear(F[R]) : function() {
            F[R] = $clear(F[R]);
            if (F[Q]) {
                F[Q] = false;
                F.fireEvent(I, [B, P])
            }
            Q = R = null
        }.delay(F.getOverTime())
    }
    function o(P, Q) {
        if (!P || !Q) {
            F.fireEvent(C, [B, P, Q]);
            return
        }
        l(P, true);
        e.importJs(Q)
    }
    function K(Q, T) {
        var R;
        if (Q) {
            var U = F.getReferKey(),
            W = F.getDomainId(),
            P = F.getDm(),
            S = Q == F[x],
            V = (S ? "loginName=" + T.name + "&password=" + encodeURIComponent(T.pass) : "redirect=0") + "&callBackMethod=" + F.$name + "." + (S ? "onLogin": "onLogout");
            S && T.expire && (V += "&expire=" + F[d]);
            U && (V += "&refer=" + U);
            W && W > 0 && (V += "&domainid=" + W);
            V += "&dm=";
            var R = a[Q];
            R += (R.indexOf("?") < 0 ? "?": "&") + V + "&" + $time()
        }
        return R
    }
    F.login = function(V, U) {
        if (V) {
            new Event(V).preventDefault()
        }
        var Q = F[x];
        if (F[Q + q.ing] == true) {
            F.fireEvent(s, [Q])
        } else {
            if (!F.form) {
                F.form = U
            }
            if (F.form) {
                var S = j(),
                R = S && S[c] ? S[c].value: "",
                T = S && S[H] ? S[H].value: "",
                P = S && S[M] ? S[M].checked: false;
                if (R == S[c].defaultValue) {
                    R = ""
                }
                if (y(R, T)) {
                    o(Q, K(Q, {
                        name: R,
                        pass: T,
                        expire: P
                    }))
                }
            }
        }
    };
    F.logout = function(Q) {
        if (Q) {
            new Event(Q).preventDefault()
        }
        var P = F[v];
        if (F[P + q.ing] == true) {
            F.fireEvent(s, [P])
        } else {
            o(P, K(P))
        }
    };
    F.onLogin = function(Q, R) {
        var P = F[x];
        if (Q && (parseInt(Q.status) == 227 || parseInt(Q.status) == 229)) {
            if (!F.SSO_ENABLE || F[p]) {
                N(P, true, F[i])
            }
            if (F.SSO_ENABLE) {
                F.doSync(P)
            }
        } else {
            N(P, false, Q)
        }
    };
    F.onLogout = function(Q, R) {
        var P = F[v];
        if (Q && parseInt(Q.status) == 264) {
            if (!F.SSO_ENABLE || F[p]) {
                N(P, true, F[i])
            }
            if (F.SSO_ENABLE) {
                F.doSync(P)
            }
        } else {
            N(P, false, Q)
        }
    };
    function A() {
        var P = F.getRedirectUrl();
        if (P && P != "") {
            window.location.href = P
        }
    }
    function N(P, S, R) {
        l(P, false);
        if (S) {
            if (b.is_safari) {
                var Q;
                b.each($splat(F.getDomains()),
                function(U, T) {
                    Q = O.substitute({
                        0 : U,
                        1 : P,
                        2 : b.encode(T > 0 ? Q: location.href)
                    })
                });
                Q = P == F[x] ? w.substitute({
                    0 : b.encode(Q)
                }) : Q;
                e.redirect(Q);
                return
            }
            F.update();
            F.fireEvent(k, [P, R]);
            if (R && !isNaN(R) && R > 0) {
                F.directTimer = A.delay(R)
            }
        } else {
            F.fireEvent(f, [B, P, R])
        }
    }
    function r(P, R, Q) {
        if (F.SSO_ENABLE && !F[p]) {
            N(P, R, Q)
        } else {
            F.directTimer = b.clear(F.directTimer);
            A()
        }
        if (R) {
            F.fireEvent(L, [P, Q])
        } else {
            F.fireEvent(f, [n, P, R, Q])
        }
    }
    F.doSync = function(Q) {
        var S;
        if (Q == F[x]) {
            S = "onLoginSync"
        } else {
            if (Q == F[v]) {
                S = "onLogoutSync"
            } else {
                F.fireEvent(C, [n, Q]);
                return
            }
        }
        var R = (F.getSsoHost() || (window.opera ? "http://" + location.hostname: (F.getDomainId() == 2 ? D.jc: D.space))) + h.substitute({
            0 : Q,
            1 : F.$name + "." + S,
            2 : (F.getLc() || ""),
            3 : (F.getDm() || ""),
            4 : ((F.getDomains()).join(",") || "")
        });
        R += (R.indexOf("?") < 0 ? "?": "&") + b.time();
        var T = "ku6_sso_frame",
        P = $(T, true);
        if (!P) {
            P = new Element("iframe", {
                id: T,
                name: T,
                styles: {
                    width: 0,
                    height: 0,
                    position: "absolute",
                    zIndex: -1,
                    visibility: "hidden"
                }
            }).inject(document.body)
        }
        P.set("src", R);
        P = null
    };
    F.onLoginSync = function(Q, P) {
        r(F[x], Q, P)
    };
    F.onLogoutSync = function(Q, P) {
        r(F[v], Q, P)
    };
    F.update = function() {
        var P;
        try {
            P = F.iUser()
        } catch(Q) {}
        if (F.panel) {
            try {
                F.iRenderPanel(P)
            } catch(Q) {
                F.fireEvent(C, [J, "panel", Q])
            }
        }
        if (F.ONERROR) {
            F[P ? "removeEvent": "addEvent"](f, F.ONERROR)
        }
        F.fireEvent(m, [P]);
        return this
    };
    F.init = function(P) {
        F.panel = P || "";
        if (!F.ONERROR) {
            F.ONERROR = function(Q, U, S, V) {
                if (Q == u && F.form && S) {
                    alert(S.msg || _("server 500"));
                    var R = j(),
                    T = R[S.name];
                    if (T) {
                        T.focus()
                    }
                    R = null;
                    T = null
                }
            }
        }
        F.fireEvent(t);
        F.update();
        F.init = null;
        delete F.init
    };
    F.iRenderPanel = function(Q) {
        var P = $(F.panel, true);
        if (P) {
            F.form = null;
            $(P).set("html", Q ? (e.getTpl("LG.INFO") || _T("lg_info")).substitute({
                nickTitle: Q.nick.escHtmlEp(),
                nick: Q.nick,
                ico: Q.smallIcon,
                middleIco: Q.middleIcon,
                bigIco: Q.bigIcon,
                podcast: e.getPodcastUrl(Q.id, Q.url),
                space: e.getSpaceUrl(Q.id, Q.url),
                spaceHome: e.getSpaceHomeUrl(Q.url)
            }) : ((e.getTpl("TPL.LG.BOX") || "") || _T("lg_box")))
        }
        return this
    };
    F.iUser = function() {
        var P = b.User ? b.User.Passport: null;
        return P ? P.get() : null
    };
    Ku6.Logon = F
})(using("App.Logon"));
using("App.I18N").zh_CN = {
    "no comment": "��û���������ۣ���4�8�ɳ���ɡ�",
    "fetch fail": "��ȡʧ��",
    "afresh fetch": "���»�ȡ",
    "subscribe success": "���ĳɹ�",
    "favorite success": "�ղسɹ�",
    "you have voted": "���Ѿ�Ͷ��Ʊ��,�벻Ҫ�ظ�ͶƱ",
    "click too quickly": "��ĵ����,�벻Ҫ�ظ����",
    "can not subscribe self": "�Բ���,���ܶ����Լ�",
    "server 500": "������æ�����Ժ����ԣ����벥�͹���Ա(http://v.ku6.com/admin)jϵ��",
    "verify code:": "��������֤�룺",
    refresh: "ˢ��",
    "login 500": "������æ,��¼ʧ��,���Ժ��ˢ��ҳ������",
    "login timeout": "�Բ���,��¼��ʱ,��ˢ��ҳ�������",
    "logout timeout": "�Բ���,�ǳ�ʱ,��ˢ��ҳ�������",
    "login processing": "���ڵ�¼���Ժ�...",
    "logout processing": "�����˳����Ժ�...",
    "login now ?": "�Բ���,��û�е�¼,�Ƿ�b����¼?",
    "play quicklist": "���ŵ㲥��",
    "add to quicklist": "��ӵ��㲥��",
    senior: "�߼�",
    "refers:": "�������ü�¼��",
    "playlist embody:": "�ղظ���Ƶ��ר����"
};
function _(a) {
    if (App.I18N.zh_CN[a]) {
        a = App.I18N.zh_CN[a]
    }
    return a
};
Browser.Features.range = document.implementation.hasFeature("Range", "2.0");
Element.implement({
    show: function(a) {
        if (a == 2 || a == 1) {
            this.setStyle("visibility", "visible");
            if (a == 2) {
                this.setStyle("display", "")
            }
        } else {
            this.setStyle("display", "")
        }
        return this
    },
    hide: function(a) {
        if (a == 2 || a == 1) {
            this.setStyle("visibility", "hidden");
            if (a == 2) {
                this.setStyle("display", "none")
            }
        } else {
            this.setStyle("display", "none")
        }
        return this
    },
    visible: function() {
        return this.show(1)
    },
    hidden: function() {
        return this.hide(1)
    },
    toggle: function(c) {
        var a = this.getStyle("visibility") == "hidden" ? "visible": "hidden",
        b = this.getStyle("display") == "none" ? "": "none";
        if (c == 2 || c == 1) {
            this.setStyle("visibility", a);
            if (c == 2) {
                this.setStyle("display", b)
            }
        } else {
            this.setStyle("display", b)
        }
        return this
    },
    isHide: function(a) {
        if (a == 2 || a == 1) {
            return this.getStyle("visibility") == "hidden" && ((a == 2 && this.getStyle("display") == "none") || true)
        }
        return this.getStyle("display") == "none"
    },
    makeMovable: function(a) {
        return new Drag(this, $merge({
            modifiers: {
                x: "left",
                y: "top"
            },
            preventDefault: true
        },
        a))
    },
    hideOutline: function() {
        if (Browser.Engine.trident) {
            this.hideFocus = true
        } else {
            this.setStyle("outline", "none")
        }
        return this
    },
    central: function() {
        var b = {
            size: window.getSize(),
            scroll: window.getScroll()
        };
        var a = this.getSize();
        this.setStyles({
            position: "absolute",
            left: b.scroll.x + (b.size.x - a.x) / 2,
            top: b.scroll.y + (b.size.y - a.y) / 2,
            zIndex: 1002
        });
        return this
    }
});
String.implement({
    escHtmlEp: function() {
        return this.replace(/[&'"<>\/\\\-\x00-\x1f\x80-\xff]/g,
        function(a) {
            return "&#" + a.charCodeAt(0) + ";"
        })
    },
    escHtml: function(a) {
        return (a === true ? this.replace(/<br\s*\/?>/gi, "\n") : this).replace(/[&'"<>\/\\\-\x00-\x09\x0b-\x0c\x1f\x80-\xff]/g,
        function(b) {
            return "&#" + b.charCodeAt(0) + ";"
        }).replace(/\r\n/g, "<br/>").replace(/\n/g, "<br/>").replace(/\r/g, "<br/>").replace(/ /g, "&nbsp;")
    }
}); (function(b) {
    var a = "http://v{mod}.stat.ku6.com",
    c = "http://stat{mod}.888.ku6.com";
    b.adFrag = "./flightview/fetchFrag/{id}.html";
    b.comment = Ku6.Urls.comment + "/pod{type}-{id}.html";
    b.plVideos = "/playlistVideo.htm?t=list";
    b.superPlaylists = "/playlist.htm?t=superlist";
    b.incomes = c + "/fetch.do?m=getUserInfo";
    b.setVPlayCount = a + "/dostatv.do?method=setVideoPlayCount";
    b.getVPlayCount = a + "/dostatv.do?method=getVideoPlayCount";
    b.setPLPlayCount = a + "/dostatc.do?method=setCardPlayCount";
    b.getPLPlayCount = a + "/dostatc.do?method=getCardPlayCount";
    b.setVideoUp = a + "/dostatv.do?method=setVideoUp";
    b.setVideoDown = a + "/dostatv.do?method=setVideoDown";
    b.getVideoRefer = a + "/dostatv.do?method=getVideoRefers";
    b.getPlayListRefer = a + "/dostatc.do?method=getCardRefers";
    b.getVideoEmBody = "http://special.so.ku6.com/playlistvideo/p";
    b.subUserAdd = "/sub.htm?t=usave";
    b.subPlayListAdd = "/sub.htm?t=psave";
    b.favVideoAdd = "/fav.htm?t=vsave";
    b.favPlayListAdd = "/fav.htm?t=psave";
    b.playlistIn = "/playlistVideo.htm?t=locPlaylistByVideoId";
    b.videoInfo = "/video.htm?t=getVideosByIds"
})(using("Ku6.Actions")); (function(b) {
    var c = Ku6.User,
    a = Ku6.Utils;
    b.visitorMode = function() {
        var e = b.getUser(c.TYPE_PASSPORT),
        d = App.User;
        if (e) {
            return (e.uid == d.uid) ? 3 : 2
        }
        return 0
    };
    b.toggleLogonWin = function(g, k, f) {
        var e = App.Logon,
        d = e ? $(App.Logon.panel) : null;
        if (d) {
            var h = $type(k),
            j = h == "element",
            i = j ? $(k).getCoordinates() : {};
            if (j || h == "object") {
                d.setPosition(j ? {
                    x: i.left - (f ? d.getSize().x - i.width: 0),
                    y: i.top + i.height
                }: k)
            } else {
                d.central()
            }
            d[(arguments.length == 0 || g === null) ? "toggle": (g === true) ? "hide": "show"](1);
            if (!d.isHide(1)) {
                d.getElement("form input[name=account]").focus()
            }
        }
        d = null
    }
})(using("App")); (function(a) {
    a.tasks = [];
    a.interval = App.TASK_INTERVAL || 600;
    a.run = function(b) {
        if (this.waitTimer) {
            $clear(this.waitTimer);
            delete this.waitTimer
        }
        if ($type(b) == "number" && b > 0) {
            this.waitTimer = function() {
                this.ableRun = true;
                this.listen()
            }.delay(b, this)
        } else {
            this.ableRun = true;
            this.listen()
        }
    };
    a.listen = function() {
        if (!this.timer) {
            this.timer = this.process.periodical(this.interval, this)
        }
    };
    a.process = function() {
        if (this.ableRun && this.tasks.length > 0) {
            var b = this.tasks.shift();
            if ($type(b) == "function") {
                b()
            }
        } else {
            if (this.timer) {
                $clear(this.timer);
                delete this.timer
            }
        }
    };
    a.add = function(b) {
        if ($type(b) == "function") {
            this.tasks.include(b)
        }
        this.listen()
    }
})(using("App.TaskQueue")); (function(b) {
    var a = new Class({
        Implements: [Events, Options],
        options: {},
        initialize: function(d) {
            this.setOptions(d);
            this.$name = this.options.name
        },
        add: function(d) {
            if (this.options.url && $defined(d)) {
                if (!this.xhr) {
                    this.xhr = new Request({
                        url: this.options.url,
                        onSuccess: this.response.bind(this),
                        onFailure: this.response.bind(this)
                    })
                }
                if (this.xhr) {
                    this.xhr.post({
                        id: d
                    })
                }
            }
        },
        response: function(i, h) {
            var f;
            try {
                f = JSON.decode(i)
            } catch(g) {}
            if (f && f.status == 1) {
                this.fireEvent("success")
            } else {
                this.fireEvent("fail", [f ? f.status: 0, f ? f.data: ""])
            }
        }
    });
    var c = Ku6.Actions;
    b.Favorite = new a({
        name: b.$name + ".Favorite",
        url: (!b.playListIndex ? c.favVideoAdd: c.favPlayListAdd),
        tip: false,
        onSuccess: function() {
            if (this.options.tip) {
                alert(_("favorite success"))
            }
        },
        onFail: function(d, e) {
            if (this.options.tip) {
                alert(e || _("server 500"))
            }
        }
    });
    b.Subscribe = new a({
        name: b.$name + ".Subscribe",
        url: c.subUserAdd,
        onSuccess: function() {
            alert(_("subscribe success"))
        },
        onFail: function(d, e) {
            alert(e || _("server 500"))
        }
    })
})(using("App")); (function(a) {
    $extend(a, new Events);
    a.frameFrag = [];
    a.getWraper = function(c, b) {
        return c && this.cache && Hash.has(this.cache, c) ? "gg" + c: null
    };
    a.fetch = function(c, b) {
        if ($type(c) != "array" || c.length == 0) {
            return false
        }
        if ($type(b) == "array") {
            this.frameFrag.combine(b)
        }
        if (!this.xhr) {
            this.xhr = new Request({
                url: Ku6.Actions.adFrag.substitute({
                    id: c.join(",")
                }),
                onSuccess: this.onFetch.bind(this)
            })
        }
        this.xhr.get()
    };
    a.onFetch = function(d) {
        var b;
        try {
            b = JSON.decode(d)
        } catch(c) {}
        if (b && b.status == 1 && b.data && b.data.list) {
            if (!this.cache) {
                this.cache = {}
            }
            $splat(b.data.list).each(function(e, f) {
                if (e && e.id) {
                    this.cache[e.id] = e.content
                }
            },
            this);
            if ($type(this.sizeMath) != "regexp") {
                this.sizeRegExp = new RegExp("<script>\\s*FRAGSIZE\\s*=\\s*\\[\\s*(\\d+)\\s*,\\s*(\\d+)\\s*\\]\\s*<\\/script>", "i")
            }
            Hash.each(this.cache,
            function(i, e) {
                if (!i || !i.trim()) {
                    return
                }
                if ($splat(this.frameFrag).contains(e.toInt())) {
                    var f;
                    if (Browser.Engine.trident) {
                        f = "javascript:(function(){document.open();document.domain='ku6.com';var a=window.parent." + this.$name + ";document.write(a.getIframeHtml());document.close();a.renderIframe(" + e + ")})()"
                    } else {
                        if (Browser.Engine.presto) {
                            f = "javascript:(function(){document.open();document.domain='ku6.com';document.close();window.parent." + this.$name + ".renderIframe(" + e + ")})()"
                        }
                    }
                    var g = i.trim().match(this.sizeRegExp);
                    if (!g || g.length != 3) {
                        g = [null, 0, 0]
                    }
                    var h = new IFrame({
                        id: "ifm_gg" + e,
                        src: f || "javascript:''",
                        width: g[1],
                        height: g[2],
                        scrolling: "no",
                        frameborder: 0
                    }).inject($(this.getWraper(e)));
                    if (Browser.Engine.webkit419 || (Browser.Engine.webkit && !window.getSelection().getRangeAt)) {
                        h.addEvent("load", this.renderIframe.bind(this, e));
                        h.src = "/html/safari/blank.html"
                    } else {
                        if (!Browser.Engine.trident && !Browser.Engine.presto) {
                            this.renderIframe(e)
                        }
                    }
                } else {
                    this.renderDiv(e)
                }
            },
            this);
            this.fireEvent("render", [this.cache])
        } else {
            this.fireEvent("fetchFail", [b])
        }
    };
    a.iframeHtml = '<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><html><head xmlns="http://www.w3.org/1999/xhtml"><meta http-equiv="Content-Type" content="text/html; charset=GBK" /><script>document.domain="ku6.com"<\/script></head><body style="margin:0;padding:0;">{html}</body></html>';
    a.fixDocWrite = '<script>document.wt=document.write;document.write=function(m){var s=document.createElement("span");s.innerHTML=m;document.body.appendChild(s)}<\/script>';
    a.getIframeHtml = function(b) {
        return (b ? this.fixDocWrite: "") + this.iframeHtml.substitute({
            html: b || ""
        })
    };
    a.renderIframe = function(f) {
        if (Hash.has(this.cache, f)) {
            var b = $("ifm_gg" + f);
            if (b) {
                try {
                    var d = b.contentWindow.document;
                    d.open();
                    d.write(this.getIframeHtml(this.cache[f]));
                    d.close()
                } catch(c) {}
            }
        }
    };
    a.renderDiv = function(c) {
        var b = this.getWraper(c);
        if (b && $(b) && $type(this.cache[c]) == "string") {
            $(b).set("html", this.cache[c]).show()
        }
    }
})(using("App.AdFrag")); (function(b) {
    var a = new Class({
        Implements: [Options, Events],
        options: {},
        initialize: function(c) {
            this.setOptions(c);
            this.$name = this.options.name
        },
        getQuery: function() {
            var c = this.options.url + "&n=" + this.$name + ".response&cp=null";
            if ($type(this.options.data) == "object") {
                c += "&" + Hash.toQueryString($unlink(this.options.data))
            }
            return c
        },
        request: function() {
            if (!this.busy && this.$name && this.options.url) {
                this.busy = true;
                this.js = new Asset.javascript(this.getQuery())
            }
            return this
        },
        response: function() {
            this.busy = false;
            if (this.js) {
                this.js.destroy();
                delete this.js
            }
            if (arguments.length > 0) {
                this.fireEvent("success", $A(arguments))
            } else {
                this.fireEvent("failure", $A(arguments))
            }
        }
    });
    b.PlayCount = new Class({
        Extends: a
    });
    b.Income = new Class({
        Extends: a,
        getQuery: function() {
            var c = this.options.url + "&funcname=" + this.$name + ".response&cp=null";
            if ($type(this.options.data) == "object") {
                c += "&" + Hash.toQueryString($unlink(this.options.data))
            }
            return c
        }
    })
})(using("App"));
Ku6.extend(using("QuickList"), {
    playMode: (window.location.hash.substr(1) == QuickList.hash && QuickList.contains(App.VideoInfo.id)),
    play: function(b) {
        var a = $type(b) == "string" ? b: this.value[b];
        window.top.location.href = this.getPlayUrl(a);
        if (window.top.location.search.length == 0 && App.VideoInfo.id == a) {
            window.top.location.reload()
        }
    },
    playNext: function() {
        var a = App.VideoInfo.id,
        c;
        if (arguments[0] && arguments[0] != a) {
            a = arguments[0];
            c = true
        }
        var b = this.value.indexOf(a);
        if (b >= 0 && (c || b < (this.value.length - 1))) {
            this.play(c ? b: (b + 1))
        }
    }
});
using("App").QuickList = QuickList; (function(a) {
    $extend(a, new Events);
    a.init = function(b) {
        var c = b || {};
        this.split = c.split || ",";
        this.key = c.key || "upOrDownHistory";
        this.duration = c.duration || 365;
        this.ableUp = c.ableUp || true;
        this.ableDown = c.ableDown || true;
        this.data = c.data || {}
    };
    a.getHistory = function() {
        var b = Cookie.read(this.key);
        if (b) {
            return b.split(this.split)
        } else {
            return []
        }
    };
    a.hasSave = function() {
        return this.getHistory().contains(this.data.id)
    };
    a.onSave = function(c, b) {
        this.processing = false;
        Cookie.write(this.key, this.getHistory().include(this.data.id).join(this.split), {
            domain: "ku6.com",
            duration: this.duration
        });
        this.fireEvent("saved", [c, b])
    };
    a.save = function(b) {
        b = $type(b) == "number" ? (b.toInt() == 1 ? 1 : 0) : 0;
        if (this.hasSave()) {
            alert(_("you have voted"))
        } else {
            if (this.processing) {
                alert(_("click too quickly"))
            } else {
                if (this[["ableUp", "ableDown"][b]] == true) {
                    var e = Ku6.Actions[["setVideoUp", "setVideoDown"][b]].substitute({
                        mod: Ku6.Utils.statPoiseServer(this.data.uid)
                    });
                    var d = "v=" + this.data.id + "&o=" + this.data.uid + "&n=" + this.$name + ".onSave&cp=" + b;
                    e += (e.indexOf("?") >= 0 ? "&": "?") + d;
                    var c = Asset.javascript.delay(10, Asset, [e]);
                    this.processing = true
                }
            }
        }
    }
})(using("App.UpDown")); (function(d) {
    var e = App.VideoInfo,
    c = Ku6.Actions,
    a = !!Browser.Engine.presto;
    d.id = e ? e.id: null;
    function f(j, g) {
        if (a) {
            var h = d.frame;
            if (h) {
                var i = h.contentWindow && typeof h.contentWindow.postMessage == "function" ? h.contentWindow: null;
                if (i) {
                    i.postMessage(JSON.encode({
                        action: j,
                        value: JSON.encode(g)
                    }))
                }
            }
        }
    }
    function b(j, h) {
        var i = d.frame;
        if (i && i.contentWindow) {
            if (a) {
                f(j, h)
            } else {
                var g = i.contentWindow.Manager;
                if (g && g[j]) {
                    g[j].call(g, h)
                }
            }
        }
    }
    d.setSkin = function(g) {
        b("setSkin", g)
    };
    if (a) {
        window.addListener("message",
        function(h) {
            var i = h.data,
            g = JSON.decode(i);
            var j = d[g.action];
            if ($type(j) == "function") {
                j.call(d, JSON.decode(g.value))
            }
        })
    }
    d.init = function(h) {
        this.frame = $(h || this.frame);
        if (d.tasks && Ku6.type(d.tasks) == "array") {
            while (d.tasks.length > 0) {
                var g = d.tasks.shift();
                if (Ku6.type(g) == "array") {
                    if (g[0] === true) {
                        f(g[1], g[2])
                    } else {
                        b(g[1], g[2])
                    }
                }
            }
        }
    };
    d.change = function(g) {
        if (g && g.id != this.id && this.frame) {
            this.frame.set("src", c.comment.substitute({
                id: g.id,
                type: g.type || "v"
            }))
        }
    }
})(using("App.CommentFrame")); (function(c) {
    var a = Ku6.Utils,
    b = 3000;
    function d() {
        $clear(c.timer);
        delete c.timer;
        delete c.loading;
        delete c.loaded;
        if (c.js) {
            c.js.destroy();
            delete c.js
        }
        if (c.el) {
            c.el.empty()
        }
    }
    c.init = function(e) {
        var f = $unlink(e || {});
        c.videoMode = (typeof f.videoMode) == "boolean" ? f.videoMode: true;
        var g = c.videoMode ? App.VideoInfo: App.ObjectInfo;
        c.uid = f.uid || g.uid;
        c.id = f.id || g.id;
        c.el = $(f.el) || c.el;
        c.max = f.max || 30;
        c.length = f.length || 80;
        d();
        if (f.auto) {
            c.request()
        }
    };
    c.request = function() {
        if (c.loading || c.loaded) {
            return
        }
        var e = Ku6.Actions[c.videoMode ? "getVideoRefer": "getPlayListRefer"].substitute({
            mod: a.statPoiseServer(c.uid)
        });
        e += "&flag=all&" + (c.videoMode ? "v": "vc") + "=" + c.id + "&n=" + c.$name + ".response&cp=0";
        c.loading = true;
        c.js = new Asset.javascript(e);
        c.timer = (function() {
            delete c.loading
        }).delay(b)
    };
    c.response = function(o) {
        $clear(c.timer);
        delete c.timer;
        delete c.loading;
        c.loaded = true;
        if (o && o.length > 0 && c.el) {
            var j = ["<dt>" + _("refers:") + "</dt>"],
            g = c.max,
            l = c.length;
            for (var h = 0,
            f = Math.min(o.length, g); h < f; h++) {
                var m = o[h],
                k = m.playcount,
                e = a.assureUrl(m.refer);
                j.push('<dd><span class="total">' + k + '</span>��: <a href="' + e + '" target="_blank" title="' + e + '">' + a.cutString(e, l, "...") + "</a></dd>")
            }
            c.el.set("html", j.join("")).show()
        }
    }
})(using("App.Refers")); (function(c) {
    var a = Ku6.Utils,
    b = 3000;
    function d() {
        $clear(c.timer);
        delete c.timer;
        delete c.loading;
        delete c.loaded;
        if (c.js) {
            c.js.destroy();
            delete c.js
        }
        if (c.el) {
            c.el.empty()
        }
    }
    c.init = function(e) {
        var f = $unlink(e || {});
        c.id = f.id || App.VideoInfo.id;
        c.el = $(f.el) || c.el;
        c.max = f.max || 30;
        d();
        if (f.auto) {
            c.request()
        }
    };
    c.request = function() {
        if (c.loading || c.loaded) {
            return
        }
        var e = Ku6.Actions.getVideoEmBody;
        e += (e.indexOf("?") >= 0 ? "&": "?") + "id=" + c.id + "&n=" + c.$name + ".response&cp=0";
        c.loading = true;
        c.js = new Asset.javascript(e);
        c.timer = (function() {
            delete c.loading
        }).delay(b)
    };
    c.response = function(h) {
        $clear(c.timer);
        delete c.timer;
        delete c.loading;
        c.loaded = true;
        if (h && h.length > 0 && c.el) {
            var g = ["<dt>" + _("playlist embody:") + "</dt>"],
            k = c.max;
            for (var f = 0,
            m = Math.min(h.length, k); f < m; f++) {
                var j = h[f],
                l = j.name || "",
                e = a.getPlaylistUrl(j.id);
                g.push('<dd><a href="' + e + '" target="show_v" title="' + l.escHtmlEp() + '">' + l.escHtml() + "</a></dd>")
            }
            c.el.set("html", g.join("")).show()
        }
    }
})(using("App.PLEmbody")); (function(a) {
    $extend(a, new Events);
    a.schemes = ["DDDDDD", "FF6766", "FC774A", "FFCB65", "99CC67", "CB66CC", "FF679A", "66CBFF", "66CDCC"];
    a.scheme = 0;
    a.count = 8;
    a.flashUrl = "http://player.ku6.com/refer/{vid}/v.swf";
    a.skinUrl = "http://static.ku6.com/img/podcast/skin4/{scheme}n.gif";
    a.init = function(b) {
        var c = b || {};
        this.vid = c.vid || this.vid;
        this.id = c.id || this.id;
        this.colorMod = $(c.colorMod) || this.colorMod;
        return this
    };
    a.use = function(b) {
        b = parseInt(b);
        if (b == b && b < this.count && b != this.scheme) {
            var c = this.scheme;
            if (this.colorMod) {
                this.colorMod.getElements("a").each(function(d, e) {
                    if (e == c) {
                        d.removeClass("sel")
                    } else {
                        if (e == b) {
                            d.addClass("sel")
                        }
                    }
                })
            }
            this.scheme = b;
            this.preview()
        }
    };
    a.preview = function() {
        if (!Browser.loaded) {
            this.delay(10, this);
            return
        }
        if (!this.builded) {
            var d = [];
            d.push('<span class="skin"><img/></span>');
            for (var c = 0; c < this.count; c++) {
                d.push('<a href="#" skin="' + c + '" ' + (c == this.scheme ? 'class="sel"': "") + '><span class="s' + (c + 1) + '"></span></a>')
            }
            if (this.colorMod) {
                this.colorMod.addEvent("click",
                function(h) {
                    h.stop();
                    var g = $(h.target);
                    var f = g.tagName.toLowerCase();
                    if (f == "span") {
                        g = g.getParent();
                        f = g.tagName.toLowerCase()
                    }
                    if (f == "a" && !g.hasClass("sel")) {
                        this.use(g.get("skin"))
                    }
                }.bindWithEvent(this)).set("html", d.join(""))
            }
            this.builded = true
        }
        var b = this.skinUrl.substitute({
            scheme: this.scheme,
            border: "n"
        });
        if (!this.previewImg && this.colorMod) {
            this.previewImg = this.colorMod.getElement("span.skin img")
        }
        this.previewImg.set("src", b);
        this.updateCode()
    };
    a.updateCode = function() {
        var b = this.flashUrl.substitute({
            id: this.id,
            vid: this.vid
        });
        if (this.scheme > 0) {
            b += "&color=" + this.schemes[this.scheme]
        }
        this.fireEvent("update", [b])
    }
})(using("App.CustomColor")); (function(b) {
    var a = Ku6.Utils,
    d = encodeURIComponent,
    c = ["kx|http://www.kaixin001.com/~repaste/repaste.php?rtitle={title}&rurl={url}&rcontent={content}", "xn|http://share.xiaonei.com/share/buttonshare.do?link={url}&title={title}", "db|http://www.douban.com/recommend/?url={url}&title={title}&rc=1", "fb|http://www.facebook.com/share.php?u={url}&t={title}", "tt|http://twitter.com/home?status={title}{url}", "qq|http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url={url}", "xl|http://v.t.sina.com.cn/share/share.php?c=spr_web_bd_ku6_weibo&url={url}&appkey=612700343&title={title}&source=%E9%85%B76%E7%BD%91&sourceUrl=http%3A%2F%2Fwww.ku6.com%2F&content=gbk&pic={pic}", "51|http://share.51.com/share/out_share_video.php?vaddr={player}&title={title}&from=ku6", "bsh|http://bai.sohu.com/share/blank/addbutton.do?from=ku6&link={url}", "tb|http://share.jianghu.taobao.com/share/addShare.htm?url={url}&tracelog=ku6", "bd|http://apps.hi.baidu.com/share/?url={url}", "bdi|http://tieba.baidu.com/i/sys/share?link={url}&type=video&title={title}&content={player}", "xy|http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?to=xiaoyou&url={url}", "ty|http://www.tianya.cn/new/share/compose.asp?itemtype=tech&item=665&strTitle={title}&strFlashURL={player}&strFlashPageURL={url}", "163|http://t.163.com/article/user/checkLogin.do?link=http://www.ku6.com/&source=%E9%85%B76%E7%BD%91&info={title} {url}", "sht|http://t.sohu.com/third/post.jsp?url={url}&title={textTitle}&content=utf-8&pic={pic}", "mop|http://tt.mop.com/share/shareV.jsp?title={oriTitle}&flashUrl={player}&pageUrl={url}", "qqt|http://v.t.qq.com/share/share.php?title={textTitle}&url={url}&appkey=c33539671f6c4fa0bbe689037b7ac21e&site=http%3A%2F%2Fwww.ku6.com%2F&"];
    b.init = function(u) {
        var k = u || {},
        r = App.VideoInfo,
        q = k.title || r.title,
        p = document.createElement("div"),
        h = k.url || a.getVideoUrl(r.id),
        s = k.player || a.getPlayerUrl(r.id),
        t = k.url || a.getCoverUrl(r.cover),
        m;
        p.innerHTML = q;
        m = d(p.innerHTML);
        for (var o = 0,
        l = c.length; o < l; o++) {
            var g = c[o].split("|"),
            f = g[0],
            j = $("out_" + f),
            e = g[1].substitute({
                title: d(f == "tt" ? "��������6��Ƶ��" + q + " ": q),
                oriTitle: (f == "tt" ? "��������6��Ƶ��" + q + " ": q),
                textTitle: m,
                url: d(h),
                player: d(s),
                content: d(q),
                oriContent: q,
                pic: d(t)
            });
            if (j) {
                j.set("href", e);
                if (!g[2] || g[2] != "no") {
                    j.set("target", "w_out_" + f)
                }
            }
        }
    }
})(using("App.SNSShare")); (function(c) {
    var d = App.VideoInfo,
    b = App.NO_REL_VIDEO_LANG || "���������Ƶ",
    a = using("App.RunTime");
    c.init = function(e) {
        delete c.loaded;
        delete c.cache;
        var f = e || {};
        c.vid = f.vid || d.id;
        c.channel = f.channel || d.channel;
        c.title = f.title || d.title;
        c.tag = f.tag || d.tag;
        c.max = f.max || App.REL_V_SIZE || 6;
        c.update = $(f.update) || $("relVideosModule");
        c.wait = f.wait || 50;
        c.dynamic = f.dynamic === true;
        if (this.js) {
            c.js.destory();
            delete c.js
        }
        if (f.auto) {
            c.load()
        }
    };
    c.load = function() {
        if (c.loaded) {
            return
        }
        if (!c.js) {
            var e = d.soHost + "/s?q=" + encodeURIComponent((c.tag || "").clean()) + "&p=1&s=" + c.max + "&cid=" + c.channel + "&vid=" + c.vid + "&ver=2&fn=" + c.$name + ".response&cp=0&title=" + encodeURIComponent(c.title);
            c.js = App.importJs(e)
        }
    };
    c.response = function(h) {
        c.loaded = true;
        c.cache = h || {};
        c.showTJ();
        if (App.videoMode && a.NEED_SHOW_REL_PL) {
            c.showPL()
        }
        var f = c.dynamic,
        e = f ? (c.cache.data && c.cache.data.count > 0) : (c.update ? c.update.getChildren("dl").length: 0);
        if (f && c.update) {
            c.update.set("html", e ? this.getHtml() : b)
        }
        if (e) {
            var g = App.QuickList;
            if (g) {
                g.render(c.update)
            }
            if (f && a.NEED_SHOW_REL) {
                App.showRelatives()
            }
        }
    };
    c.getHtml = function() {
        var i = Ku6.Utils,
        f = App.QuickList,
        g = [],
        j = 0,
        e = c.max;
        $splat(c.cache.data.list).each(function(h) {
            if (h && h.vid && j < e) {
                var k = i.getVideoUrl(h.vid) || "#",
                l = h.title.escHtmlEp();
                g.push("<dl>");
                g.push('<dd><a href="#' + h.vid + '" title="' + _("add to quicklist") + '" class="plus" onclick="' + f.$name + '.toggle(event)"></a>');
                g.push('<a href="' + k + '" title="' + l + '"><img src="' + i.assureUrl(i.getCoverUrl(h.coverUrl)) + '" /></a></dd>');
                g.push('<dt><a href="' + k + '" title="' + l + '">' + h.title.escHtml() + "</a></dt>");
                g.push("<dd>����: " + (h.playCount || 0) + "</dd>");
                g.push("</dl>");
                j++
            }
        });
        if (j == 0) {
            g.push('<p class="noZj">���������Ƶ</p>')
        }
        return g.join("")
    };
    c.showTJ = function() {
        if (c.tjCreated) {
            return
        }
        if (c.cache.tuijian) {
            var g = $("rlTJ");
            if (g) {
                var e = [],
                h = c.cache.tuijian,
                f = h.next;
                if (f) {
                    e.push('<a href="' + f.link + '" title="' + (f.tag || "").escHtmlEp() + '" target="_blank" class="next">' + (f.title || "").escHtml() + "</a>")
                }
                g.set("html", e.join(""))
            }
        }
        c.tjCreated = true
    };
    if (App.videoMode) {
        c.showPL = function() {
            if (c.plCreated) {
                return
            }
            if (!c.loaded) {
                a.NEED_SHOW_REL_PL = true
            } else {
                delete a.NEED_SHOW_REL_PL;
                var e = App.QuickList.playMode === true ? {
                    qlMode: true
                }: {
                    data: (c.cache ? c.cache.special: null)
                };
                App.ListModule.init(e);
                c.plCreated = true
            }
        }
    }
})(using("App.RelativeVideo")); (function(a) {
    using("Ku6").toString = a.toString = function() {
        return "Author : \u5f71\u4e4b\u8ff7\u60d1\nQQ : 9537905\nMSN : zfkun@msn.com"
    }
})(using("App"));
App.preStart = function() {
    var z = this.ObjectInfo,
    m = this.VideoInfo,
    B = this.Player,
    u = this.playListIndex,
    T = using("App.RunTime"),
    E = App,
    b = Ku6,
    D = b.Plugin,
    r = b.Actions,
    d = b.Utils,
    S = b.User;
    var A = "LG_INIT_FN",
    l = "originCache";
    T[A] = function() {
        var i = b.id("userInfo", true);
        if (i) {
            T[l] = i.innerHTML
        }
        i = null;
        App.Logon.removeEvent("init", T[A]);
        T[A] = null
    };
    this.Logon.setReferKey("podcast" + App.videoMode ? "v": "p").addEvents({
        init: T[A],
        update: function(i) {
            if (i) {
                App.toggleLogonWin(true)
            }
            var V = $("userInfo", true);
            if (V) {
                var n = T[l];
                if (i) {
                    n = (d.getTpl("TPL.UINFO") || _T("lg_uinfo")).substitute({
                        url: d.getSpaceUrl(i.uid, i.url),
                        nick: (i.nick || "").escHtmlEp(),
                        nick: (i.nick || "").escHtml()
                    })
                }
                $(V).set("html", n);
                V = null
            }
        },
        timeout: function(i, n) {
            alert("����ʱ,���Ժ�����...")
        },
        exception: function(i, n, V) {
            alert("�����쳣,���Ժ����� ")
        },
        busy: function(i) {
            alert("����" + {
                login: "��¼",
                logout: "�˳�"
            } [i] + "���Ժ�...")
        },
        error: function(i, V, n, W) {
            if (i == "logon") {
                alert(n ? n.msgText: "\u670d\u52a1\u5668\u5fd9")
            }
        }
    }).init("logonWin");
    var k = $merge({
        iframeFix: this.USE_IFRAME_FIX,
        autoHide: true,
        fetchDelay: 20,
        hideDelay: 200,
        action: "http://fz.v.so.ku6.com/query",
        statSuffix: "����Ƶ",
        closeLang: "�ر�"
    },
    this.ACT);
    T.actTop = new D.AutoCompleteTip($merge($unlink(k), {
        name: T.$name + ".actTop",
        input: "inp",
        minWidth: 239,
        onClick: function(V, n, W) {
            App.doSearch($("topSearchForm"), true)
        }
    }));
    T.actBottom = new D.AutoCompleteTip($merge($unlink(k), {
        name: T.$name + ".actBottom",
        input: "inp2",
        onClick: function(V, n, W) {
            App.doSearch($("bottomSearchForm"), true)
        }
    }));
    if (!u) {
        App.SNSShare.init()
    }
    var g = $("userSubBtn");
    if (g) {
        T.subClk = function(i) {
            if (i) {
                i.preventDefault()
            }
            if (this.visitorMode() <= 0) {
                if (confirm(_("login now ?"))) {
                    App.toggleLogonWin(false)
                }
            } else {
                this.Subscribe.add(u ? z.uid: m.uid)
            }
        }.bindWithEvent(this);
        g.addEvent("click", T.subClk)
    }
    if (!u) {
        this.UpDown.init({
            data: {
                id: m.id,
                uid: App.videoMode ? App.User.id: m.uid
            }
        });
        var q = this.UpDown,
        P = $("upfBtn"),
        e = $("upBtn"),
        c = $("upCount2"),
        o = $("downBtn"),
        M = $("downfBtn");
        if (q && (e || o || P || M)) {
            q.addEvent("saved",
            function(V, X) {
                var n = (X == 1 ? o: e),
                i = (X == 1 ? M: P),
                Y = (X == 1 ? null: c);
                if (n) {
                    var W = n.getElement(".total");
                    if (W) {
                        W.set("text", (parseInt(W.get("text")) || 0) + 1)
                    }
                }
                if (i) {
                    i.set("text", (parseInt(i.get("text")) || 0) + 1)
                }
                if (Y) {
                    Y.set("text", (parseInt(Y.get("text")) || 0) + 1)
                }
            });
            if (e) {
                e.addEvent("click",
                function(i) {
                    if (i) {
                        i.preventDefault()
                    }
                    q.save(0)
                }.bindWithEvent())
            }
            if (o) {
                o.addEvent("click",
                function(i) {
                    if (i) {
                        i.preventDefault()
                    }
                    q.save(1)
                }.bindWithEvent())
            }
            if (P) {
                P.addEvent("click",
                function(i) {
                    if (i) {
                        i.preventDefault()
                    }
                    q.save(0)
                }.bindWithEvent())
            }
            if (M) {
                M.addEvent("click",
                function(i) {
                    if (i) {
                        i.preventDefault()
                    }
                    q.save(1)
                }.bindWithEvent())
            }
        }
    }
    var v = $("favBtn"),
    H = $("favCount"),
    t = $("favCount2");
    if (v) {
        T.FAV_HOOK = function(n) {
            if (n) {
                n.preventDefault()
            }
            var V = !this.playListIndex,
            i = this.visitorMode();
            if (i == 3) {
                alert("�Բ���,�����ղ��Լ���" + (V ? "��Ƶ": "ר��"))
            } else {
                if (i != 2) {
                    if (confirm(_("login now ?"))) {
                        App.toggleLogonWin(false)
                    }
                } else {
                    this.Favorite.add(V ? m.id: z.id)
                }
            }
        }.bindWithEvent(this);
        v.addEvent("click", T.FAV_HOOK);
        var s = function(n) {
            var i = App.getUser(S.TYPE_PASSPORT);
            if (i) {
                App.showBtnTip(_T("fav_ok").substitute({
                    url: d.getPodcastUrl(i.uid, i.url) + "/fav.html"
                }), "fav")
            }
            if (!n) {
                if (H) {
                    H.set("text", (parseInt(H.get("text")) || 0) + 1)
                }
                if (t) {
                    t.set("text", (parseInt(t.get("text")) || 0) + 1)
                }
            }
        };
        this.Favorite.addEvents({
            success: s,
            fail: function(i, n) {
                if (i && i == -5) {
                    s(true)
                } else {
                    alert(n || _("server 500"))
                }
            }
        })
    }
    if (!u) {
        var U = $("customBtn"),
        F = $("outSideSwfCode"),
        x = $("outSideHtmlCode");
        this.CustomColor.init({
            id: z.id,
            vid: m.id,
            colorMod: "customColorModule"
        });
        if (F || x) {
            T.SHARE_OUT_HTML = this.SHARE_OUT_HTML || '<embed src="{swf}" quality="high" width="414" height="305" align="middle" allowScriptAccess="always" type="application/x-shockwave-flash"></embed>';
            this.CustomColor.addEvent("update",
            function(i) {
                if (F) {
                    F.set("value", i)
                }
                if (x) {
                    x.set("value", T.SHARE_OUT_HTML.substitute({
                        swf: i
                    }))
                }
            })
        }
        if (U) {
            U.set("text", "ѡ�񲥷�����ɫ").addEvent("click", this.toggleCustom.bindWithEvent(this))
        }
    }
    if (!u) {
        this.Refers.init({
            length: 80,
            el: "referList",
            auto: !!T.infoModExpand,
            videoMode: true
        });
        this.PLEmbody.init({
            el: "embodyList",
            auto: !!T.infoModExpand
        })
    }
    var j = this.QuickList;
    j.init();
    if (!u) {
        this.RelativeVideo.init({
            auto: true,
            dynamic: this.RELVIDEO_STATIC !== true
        })
    }
    if (!u) {
        T.COMMENT_INIT_FN = function() {
            this.CommentFrame.init()
        }.bind(this);
        if (!this.COMMENT_DEFER) {
            T.COMMENT_INIT_FN()
        } else {
            this.TaskQueue.add(T.COMMENT_INIT_FN)
        }
    }
    var G = T.adConfig;
    if (G) {
        this.AdFrag.addEvent("render",
        function(n) {
            var i = !App.AD_DEFER && !!App.VBUTTON_DEFER;
            Hash.each(App.VButtons,
            function(W, V) {
                var X = function() {
                    T["vbad" + V] = new D.VButton({
                        container: "gg" + V,
                        items: W
                    })
                };
                if (!i) {
                    X()
                } else {
                    App.TaskQueue.add(X)
                }
            })
        });
        var R = [$splat(G.list), $splat(G.js)];
        if (!this.AD_DEFER) {
            this.AdFrag.fetch(R)
        } else {
            this.TaskQueue.add(this.AdFrag.fetch.bind(this.AdFrag, R))
        }
    }
    if (!u) {
        var K = $extend({
            path: "http://img.ku6.com/common/flwin/200910151710/",
            file: ["a.jpg", "b.jpg", "c.jpg"],
            state: 1,
            target: "http://stat3.888.ku6.com/g.do?m=n&i=1599"
        },
        this.Flwin);
 
    }
    var h = this.videoMode,
    C = $("playCount"),
    f = $("playCount2"),
    y = document.getElement("#upBtn .total"),
    a = document.getElement("#downBtn .total"),
    w = $("upfBtn"),
    p = $("upCount2"),
    O = $("downfBtn"),
    J = $("referCount");
    T.playCounter = new this.PlayCount({
        name: T.$name + ".playCounter",
        url: r[u ? "getPLPlayCount": "setVPlayCount"].substitute({
            mod: d.statPoiseServer(u ? z.uid: m.uid)
        }),
        data: u ? {
            o: z.uid,
            c: z.channel,
            vc: z.id
        }: {
            o: m.uid,
            c: m.channel,
            v: m.id
        },
        onSuccess: function(n) {
            if ($type(n) == "array" && n[0]) {
                var i = n[0],
                W = i.playcount || i.count || 0;
                if (h) {
                    if (this.VideoInfo) {
                        this.VideoInfo.playCount = W
                    }
                    var V = $("ALM_C");
                    if (V && V.get("vid") == i.id) {
                        V.set("text", W)
                    }
                }
                if (C) {
                    C.set("text", W)
                }
                if (f) {
                    f.set("text", W)
                }
                if (y) {
                    y.set("text", i.upcount || 0)
                }
                if (a) {
                    a.set("text", i.downcount || 0)
                }
                if (w) {
                    w.set("text", i.upcount || 0)
                }
                if (p) {
                    p.set("text", i.upcount || 0)
                }
                if (O) {
                    O.set("text", i.downcount || 0)
                }
                if (J) {
                    J.set("text", i.refercount || i.refers || 0)
                }
                if (!h && !u && this.PlayListVideos) {
                    this.PlayListVideos.syncPlaycount(n)
                }
            }
        }.bind(this)
    });
    this.TaskQueue.add(T.playCounter.request.bind(T.playCounter));
    var L = $splat(T.WAITING_VIDS);
    if (this.RELVIDEO_STATIC === true) {
        var I = this.RelativeVideo.update;
        if (I) {
            I.getChildren("dl").each(function(n) {
                var i = d.statPoiseServer(n.get("uid"));
                if ($type(L[i]) == "array") {
                    L[i].include(n.get("vid"))
                }
            })
        }
    }
    for (var Q = 0,
    N = L.length; Q < N; Q++) {
        if (L[Q] && L[Q].length > 0) {
            T["OPlayCounter" + Q] = new this.PlayCount({
                name: T.$name + ".OPlayCounter" + Q,
                url: r.getVPlayCount.substitute({
                    mod: d.statPoiseServer(Q)
                }),
                data: {
                    v: L[Q].join("|")
                },
                onSuccess: function(i) {
                    $splat(i).each(function(n) {
                        if (n) {
                            if ($("rv_" + n.id, true)) {
                                $("rv_" + n.id).set("text", n.count || 0)
                            }
                            if ($("ov_" + n.id, true)) {
                                $("ov_" + n.id).set("text", n.count || 0)
                            }
                            if ($("hv_" + n.id, true)) {
                                $("hv_" + n.id).set("text", n.count || 0)
                            }
                        }
                    })
                }
            });
            this.TaskQueue.add(T["OPlayCounter" + Q].request.bind(T["OPlayCounter" + Q]))
        }
    }
    if (u || B.timeOut || B.started || B.finished) {
        this.TaskQueue.run()
    } else {
        this.TaskQueue.run((App.TASK_WAITING || 10000) - ($time() - B.startTime))
    }
    delete this.preStart
};
window.addEvent("domready", App.preStart.bind(App));