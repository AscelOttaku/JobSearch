<#import "../layout/layout.ftlh" as layout>

<style>
    @import url('https://fonts.googleapis.com/css?family=Press+Start+2P');

    :root {
        --color: #54FE55;
        --color2: #1a4f1a;
        --glowSize: 10px;
    }

    html, body {
        width: 100%;
        height: 100%;
        margin: 0;
        overflow: hidden;
    }

    * {
        font-family: 'Press Start 2P', cursive;
        box-sizing: border-box;
    }

    #app {
        margin: 0 -1rem;
        padding: 2rem;
        background: black;
        display: flex;
        height: 100%;
        justify-content: center;
        align-items: center;
        color: var(--color);
        text-shadow: 0px 0px var(--glowSize);
        font-size: 6rem;
        flex-direction: column;
    }

    #app .txt {
        font-size: 1.8rem;
    }

    @keyframes blink {
        0%   { opacity: 0; }
        49%  { opacity: 0; }
        50%  { opacity: 1; }
        100% { opacity: 1; }
    }

    .blink {
        animation-name: blink;
        animation-duration: 1s;
        animation-iteration-count: infinite;
    }
</style>

<@layout.layout>
    <div id="app">
        <div>403</div>
        <div class="txt">
            Forbidden<span class="blink">_</span>
        </div>
    </div>
</@layout.layout>