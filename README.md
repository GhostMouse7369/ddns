## ddns

application.yml

```yaml
top:
  laoshuzi:
    ddns:
      config:
        regionId: cn-hangzhou
        accessKeyId: <accessKeyId>
        accessKeySecret: <accessKeySecret>
        IpUrl: http://ip-api.com/json
      domain:
        laoshuzi:
          domainName: laoshuzi.top
          rrs:
          - ddns
```

