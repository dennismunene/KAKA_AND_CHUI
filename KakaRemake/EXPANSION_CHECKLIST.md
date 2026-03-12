# KAKA & CHUI - Asset Expansion Checklist

## Current Status
- **Total Assets**: 222 files, 19.9 MB
- **Completion Rate**: 73% (163/222 files complete)
- **Ready for Launch**: ✅ Yes (shapes, colors, numbers 1-10, vowels)
- **Blockers**: ❌ Vokali word images (prevents full functionality)

---

## 🔴 HIGH PRIORITY: Vokali Word Images

### Status
- **Graphics**: 0/20 ❌ (MISSING)
- **Audio**: 20/20 ✅ (READY)
- **Blocker**: YES - Visual learning mode cannot start

### Missing 20 Words

| #  | Swahili Word | English Translation | Audio Status | Image Status |
|----|----|---|---|---|
| 1  | Baba | Father | ✅ 5.7K | ❌ MISSING |
| 2  | Dawa | Medicine | ✅ 5.5K | ❌ MISSING |
| 3  | Embe | Mango | ✅ 5.3K | ❌ MISSING |
| 4  | Gari | Car | ✅ 7.0K | ❌ MISSING |
| 5  | Jiko | Cooking pot | ✅ 5.7K | ❌ MISSING |
| 6  | Kijiko | Spoon | ✅ 6.8K | ❌ MISSING |
| 7  | Kisu | Knife | ✅ 6.4K | ❌ MISSING |
| 8  | Kiti | Chair | ✅ 6.1K | ❌ MISSING |
| 9  | Kuku | Chicken | ✅ 4.7K | ❌ MISSING |
| 10 | Mama | Mother | ✅ 5.0K | ❌ MISSING |
| 11 | Meli | Ship/Boat | ✅ 6.4K | ❌ MISSING |
| 12 | Paka | Cat | ✅ 4.5K | ❌ MISSING |
| 13 | Pesa | Money | ✅ 5.7K | ❌ MISSING |
| 14 | Pete | Ring | ✅ 5.0K | ❌ MISSING |
| 15 | Pikipiki | Motorcycle | ✅ 7.5K | ❌ MISSING |
| 16 | Punda | Donkey | ✅ 4.4K | ❌ MISSING |
| 17 | Rula | Ruler | ✅ 5.3K | ❌ MISSING |
| 18 | Sufuria | Cooking pan | ✅ 8.0K | ❌ MISSING |
| 19 | Ua | Flower | ✅ 4.9K | ❌ MISSING |
| 20 | Wembe | Razor blade | ✅ 5.7K | ❌ MISSING |

### Specifications
- **Resolution**: 520×520 pixels (match existing assets)
- **Format**: PNG with transparency
- **Size**: ~50-100K per image
- **Total**: 1-2 MB addition
- **Estimated Effort**: 4-6 weeks

### Checklist
- [ ] Design/gather 20 word images
- [ ] Resize to 520×520 PNG format
- [ ] Add to `/assets/gfx/somavokali/` folder
- [ ] Test in app (Vokali Maneno Zoezi mode)
- [ ] Verify audio syncs with images

---

## 🟡 MEDIUM PRIORITY: Tarakimu Extension (11-20)

### Status
- **Graphics**: 0/10 ❌ (MISSING)
- **Audio (Soma)**: 0/10 ❌ (MISSING)
- **Audio (Zoezi)**: 0/10 ❌ (MISSING)
- **Audio (Hesabu Zoezi)**: 0/10 ❌ (MISSING)

### Missing Numbers

| #  | Swahili | Graphics | Audio Soma | Audio Zoezi | Audio Hesabu |
|----|---------|----------|-----------|------------|---|
| 11 | Kumi na 1 | ❌ | ❌ | ❌ | ❌ |
| 12 | Kumi na 2 | ❌ | ❌ | ❌ | ❌ |
| 13 | Kumi na 3 | ❌ | ❌ | ❌ | ❌ |
| 14 | Kumi na 4 | ❌ | ❌ | ❌ | ❌ |
| 15 | Kumi na 5 | ❌ | ❌ | ❌ | ❌ |
| 16 | Kumi na 6 | ❌ | ❌ | ❌ | ❌ |
| 17 | Kumi na 7 | ❌ | ❌ | ❌ | ❌ |
| 18 | Kumi na 8 | ❌ | ❌ | ❌ | ❌ |
| 19 | Kumi na 9 | ❌ | ❌ | ❌ | ❌ |
| 20 | Ishirini | ❌ | ❌ | ❌ | ❌ |

### Specifications
- **Graphics**: Follow t1-t10 style (520×520 PNG)
- **Audio**: 30 files total (10 learning + 10 exercise + 10 counting)
- **Total Size**: 2.0+ MB addition
- **Estimated Effort**: 2-3 weeks artwork + 2-3 days audio

### Checklist
- [ ] Create t11.png through t20.png (graphics)
- [ ] Record 10 "Soma" audio files (learning)
- [ ] Record 10 "Zoezi" audio files (exercises)
- [ ] Record 10 "Hesabu Zoezi" audio files (counting)
- [ ] Add to `/assets/gfx/somatarakimu/` and `/assets/mfx/Tarakimu/`
- [ ] Update app logic to reference 1-20
- [ ] Test all three learning modes

---

## 🟢 LOW PRIORITY: Color & Shape Expansion

### Colors (Optional)
**Missing**: Orange, Purple, Gray, Brown, Pink (5 colors)
- **Graphics**: 5 × 8K ≈ 40K
- **Audio**: 5 × 7K ≈ 35K
- **Total**: ~75K
- **Effort**: 1 week

**Checklist**:
- [ ] Design 5 additional color images
- [ ] Record 5 Soma audio files
- [ ] Record 5 Zoezi audio files
- [ ] Add to `/assets/gfx/rangi/` and `/assets/mfx/Rangi/`

### Shapes (Optional)
**Missing**: Pentagon, Hexagon, Diamond, Heart (4+ shapes)
- **Graphics**: 4 × 40K ≈ 160K
- **Audio**: 8 × 7K ≈ 56K
- **Total**: ~235K
- **Effort**: 1-2 weeks

**Checklist**:
- [ ] Design 4+ advanced shapes
- [ ] Record Soma + Zoezi audio
- [ ] Add to `/assets/gfx/maumbo/` and `/assets/mfx/Maumbo/`

---

## 📊 Impact Analysis

### Vokali Word Images (HIGH)
| Metric | Impact |
|--------|--------|
| **User Experience** | 🔴 CRITICAL - Blocks visual learning |
| **Learning Effectiveness** | 🔴 CRITICAL - Audio only, no visuals |
| **App Functionality** | 🔴 CRITICAL - Feature incomplete |
| **User Progression** | 🟡 Partial - Can't learn words |

### Tarakimu 11-20 (MEDIUM)
| Metric | Impact |
|--------|--------|
| **User Experience** | 🟡 Good - 1-10 works well |
| **Learning Scope** | 🟡 Limited - Only half of 20s covered |
| **Age Group** | 🟡 Limits older children (5-6 years) |
| **Premium Feel** | 🟡 Looks incomplete |

### Colors/Shapes (LOW)
| Metric | Impact |
|--------|--------|
| **User Experience** | 🟢 Nice-to-have - Core features work |
| **Learning Scope** | 🟢 Sufficient for target age (3-5) |
| **Differentiator** | 🟢 Premium/deluxe feature |

---

## 📈 Timeline & Resources

### Phase 1: Vokali Words (IMMEDIATE)
- **Duration**: 4-6 weeks
- **Resources**: 1 illustrator, 1 sound engineer
- **Deliverables**: 20 images + 0 audio (audio exists)
- **Priority**: 🔴 URGENT

### Phase 2: Tarakimu 11-20 (NEXT)
- **Duration**: 2-3 weeks artwork + 2-3 days audio
- **Resources**: 1 illustrator, 1 sound engineer
- **Deliverables**: 10 graphics + 30 audio files
- **Priority**: 🟡 HIGH

### Phase 3: Expansion (FUTURE)
- **Duration**: 1-2 weeks
- **Resources**: 1 illustrator, 1 sound engineer
- **Deliverables**: 5 colors + 4 shapes (optional)
- **Priority**: 🟢 LOW

---

## 💾 File Organization

```
/assets/
├── gfx/
│   ├── somavokali/
│   │   ├── a.png ✓
│   │   ├── e.png ✓
│   │   ├── i.png ✓
│   │   ├── o.png ✓
│   │   ├── u.png ✓
│   │   ├── bgf.jpg ✓
│   │   ├── arrow_next.png ✓
│   │   └── [ADD 20 WORD IMAGES HERE]
│   │       ├── baba.png ❌
│   │       ├── dawa.png ❌
│   │       ├── ... (18 more)
│   │       └── wembe.png ❌
│   │
│   └── somatarakimu/
│       ├── t1.png ✓
│       ├── t2.png ✓
│       ├── ... (t1-t10)
│       └── [ADD t11-t20 HERE]
│           ├── t11.png ❌
│           ├── t12.png ❌
│           ├── ... (18 more)
│           └── t20.png ❌
│
└── mfx/
    ├── Vokali/ [COMPLETE - no changes needed]
    │
    └── Tarakimu/
        ├── Tarakimu Soma/ [has 1-10]
        │   ├── Moja.aac ✓
        │   ├── ... (1-10)
        │   └── [ADD 11-20 HERE]
        │
        ├── Tarakimu Zoezi/ [has 1-10]
        │   ├── Chagua Moja.aac ✓
        │   ├── ... (1-10)
        │   └── [ADD 11-20 HERE]
        │
        └── Tarakimu Hesabu Zoezi/ [has 1-10]
            ├── Chagua Moja.aac ✓
            ├── ... (1-10)
            └── [ADD 11-20 HERE]
```

---

## ✅ Final Checklist

### Before Expansion
- [ ] Review this document
- [ ] Prioritize: Vokali words first (HIGH)
- [ ] Assign resources
- [ ] Set timeline

### Vokali Phase
- [ ] Create/gather 20 word images
- [ ] Verify 520×520 resolution
- [ ] Test with app
- [ ] Document naming conventions

### Tarakimu Phase
- [ ] Create graphics t11-t20
- [ ] Record 30 audio files
- [ ] Update app logic
- [ ] Test all modes

### Quality Assurance
- [ ] Check file sizes (no bloat)
- [ ] Verify naming conventions
- [ ] Test in app context
- [ ] Cross-check with audio

---

**Document Version**: 1.0
**Last Updated**: 2025
**Total Assets Current**: 222 files, 19.9 MB
**Target with Expansions**: ~350+ files, 23+ MB
