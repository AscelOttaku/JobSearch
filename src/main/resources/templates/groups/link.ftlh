<#import "../layout.ftlh" as layout>

<@layout.layout ; spring>
    <link rel="stylesheet" href="">
    <#assign title="Group Invite Link Generator">

    <div class="invite-generator">
        <h2>Generate Group Invite Link</h2>

        <#if groupId??>
            <form method="get" action="/groups/link/${groupId}">
                <input type="hidden" name="groupId" value="${groupId!}">
                <button type="submit" class="btn-generate">Generate Link</button>
            </form>
        </#if>

        <#if link??>
            <div class="generated-link">
                <p>Your invite link:</p>
                <input type="text" value="${link}" readonly class="link-field" id="invite-link">
                <button type="button" class="btn-copy" onclick="copyLink()">Copy</button>
            </div>
        </#if>

        <a href="/groups" class="btn btn-secondary mt-3">Back</a>
    </div>

    <style>
        .invite-generator {
            max-width: 500px;
            margin: 20px auto;
            padding: 20px;
            text-align: center;
        }

        .btn-generate {
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }

        .generated-link {
            margin-top: 20px;
            padding: 15px;
            background: #f5f5f5;
            border-radius: 4px;
        }

        .link-field {
            color: #007bff;
            width: 100%;
            padding: 8px;
            margin: 10px 0;
            border: 1px solid #ddd;
            border-radius: 4px;
        }

        .btn-copy {
            padding: 8px 15px;
            background-color: #2196F3;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
    </style>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function copyLink() {
            const input = document.getElementById('invite-link');
            input.select();
            input.setSelectionRange(0, 99999);
            document.execCommand('copy');
        }
    </script>
</@layout.layout>